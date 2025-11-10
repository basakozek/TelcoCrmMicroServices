package com.etiya.basketservice.service.concretes;

import com.etiya.basketservice.client.CatalogServiceClient;
import com.etiya.basketservice.client.CustomerServiceClient;
import com.etiya.basketservice.domain.Basket;
import com.etiya.basketservice.domain.BasketItem;
import com.etiya.basketservice.repository.BasketRepository;
import com.etiya.basketservice.service.abstracts.BasketService;
import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.responses.ActiveCampaignProductResponse;
import com.etiya.common.responses.ActiveProductOfferResponse;
import com.etiya.common.responses.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final CustomerServiceClient customerServiceClient;
    private final CatalogServiceClient catalogServiceClient;

    public BasketServiceImpl(BasketRepository basketRepository, CustomerServiceClient customerServiceClient, CatalogServiceClient catalogServiceClient) {
        this.basketRepository = basketRepository;
        this.customerServiceClient = customerServiceClient;
        this.catalogServiceClient = catalogServiceClient;
    }

    @Override
    public void add(int billingAccountId, String productOfferId) {
        // 1. Müşteri doğrulaması (İsteğin üzerine korundu)
        var billingAccount = customerServiceClient.getBillingAccountById(billingAccountId);

        // 2. Katalog servisinden 3 farklı bilgiyi çek
        // 2a. Ürünün (Offer) temel bilgileri (Fiyat, Ad)
        ProductResponse product = catalogServiceClient.getById(productOfferId);

        // 2b. Ürünün (Offer) *kendi* aktif indirimi (örn: %10)
        ActiveProductOfferResponse offerDiscount = null;
        try { offerDiscount = catalogServiceClient.getBestActiveOffer(productOfferId); } catch (Exception ignored) {}

        // 2c. Ürüne (Offer) bağlı *en iyi kampanya* indirimi (örn: %15)
        ActiveCampaignProductResponse campDiscount = null;
        try { campDiscount = catalogServiceClient.getBestActiveCampaign(productOfferId); } catch (Exception ignored) {}

        // 3. En iyi indirimi hesapla
        double offerRate = (offerDiscount != null) ? normalize(offerDiscount.getDiscountRate()) : 0.0;
        double campRate  = (campDiscount  != null) ? normalize(campDiscount.getDiscountRate())  : 0.0;
        double bestRate  = Math.max(offerRate, campRate);

        int offerId = (offerRate > 0) ? Integer.parseInt(offerDiscount.getProductOfferId()) : 0; // int'e çevrildi
        int campId  = (campRate  > 0) ? campDiscount.getCampaignProductId() : 0;

        // 4. Sepeti al veya oluştur
        var basket = basketRepository.getBasketByBillingAccountId(billingAccount.getId());
        if (basket == null) {
            basket = new Basket();
            basket.setBillingAccId(billingAccount.getId());
        }

        // 5. Sepette bu ürün (Offer) var mı? Varsa quantity++, yoksa yeni BasketItem
        BasketItem item = basket.getBasketItems().stream()
                .filter(i -> i.getProductId().equals(product.getId())) // productId artık productOfferId demek
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new BasketItem(); // Yeni BasketItem kendi UUID'sini (id) oluşturur
            item.setProductId(product.getId()); // Catalog'dan gelen ProductOffer ID'si
            item.setProductName(product.getProductName());
            item.setProductPrice(product.getPrice()); // Liste Fiyatı
            item.setQuantity(1);
            item.setDiscount(bestRate);
            item.setProductOfferId(offerId);
            item.setCampaignProductId(campId);
            basket.getBasketItems().add(item);
        } else {
            // Varsa: adedi artır ve indirim/fiyat bilgilerini GÜNCELLE
            item.setQuantity(item.getQuantity() + 1);
            item.setProductPrice(product.getPrice()); // Fiyat değişmişse günceller
            item.setDiscount(bestRate); // En iyi indirimi günceller
            item.setProductOfferId(offerId);
            item.setCampaignProductId(campId);
        }

        // 6. Sepet toplamını yeniden hesapla
        recalcBasketTotal(basket);

        // 7. Sepeti Redis'e kaydet
        basketRepository.addItem(basket);
    }

    @Override
    public void deleteItem(int billingAccountId, String basketItemId) {
        // 1. O anki billingAccount'a ait sepeti Redis'ten getir.
        var basket = basketRepository.getBasketByBillingAccountId(billingAccountId);
        if (basket == null) {
            // Sepet zaten yoksa, silinecek bir şey de yoktur.
            return;
        }
        // 2. Sepetteki ürün listesinde (basket.getBasketItems()),
        //    controller'dan gelen 'basketItemId' ile eşleşen ürünü bul.
        Optional<BasketItem> itemToRemove = basket.getBasketItems().stream()
                .filter(item -> item.getId().equals(basketItemId))
                .findFirst();

        // 3. Ürün bulunduysa...
        if (itemToRemove.isPresent()) {
            // 3a. Ürünü sepetin listesinden çıkar.
            basket.getBasketItems().remove(itemToRemove.get());
            // 3b. Sepetin toplam fiyatını (totalPrice) güncel (azalan) fiyata göre yeniden hesapla.
            recalcBasketTotal(basket);
            // 3c. Sepetin (artık ürünü silinmiş olan) GÜNCEL halini Redis'e geri kaydet.
            // Not: 'addItem' metodu, sepetin ID'sini (basket.getId()) key olarak kullandığı için
            // var olan sepetin üzerine yazar (yani update/güncelleme işlemi yapar).
            basketRepository.addItem(basket); //
        } else {
            throw new BusinessException("Basket item not found with id: " + basketItemId);
        }
    }

    @Override
    public void clearBasket(int billingAccountId) {
        // 1. O anki billingAccount'a ait sepeti Redis'ten getir.
        var basket = basketRepository.getBasketByBillingAccountId(billingAccountId);
        // 2. Sepet varsa...
        if (basket != null) {
            // 2a. Sepetteki ürün listesini (basket.getBasketItems()) tamamen temizle (.clear()).
            basket.getBasketItems().clear();
            // 2b. Sepetin toplam fiyatını 0 olarak yeniden hesapla.
            recalcBasketTotal(basket);
            // Sepetin boş halini kaydet
            basketRepository.addItem(basket); //
        }
        // Sepet yoksa (basket == null) zaten boş demektir, bir şey yapmaya gerek yok.
    }

    private void recalcBasketTotal(Basket basket) {
        double total = 0.0;
        for (var it : basket.getBasketItems()) {
            double unit = it.getDiscountedPrice(); // productPrice*(1-discount)
            total += unit * it.getQuantity();
        }
        basket.setTotalPrice(total);
    }

    private double normalize(double rate) {
        return rate > 1.0 ? rate / 100.0 : Math.max(0.0, Math.min(1.0, rate));
    }

    @Override
    public Map<String, Basket> getAll() {
        return basketRepository.getAll();
    }
}
