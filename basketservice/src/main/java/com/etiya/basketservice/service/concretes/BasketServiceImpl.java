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

        String offerId = (offerRate > 0 && offerDiscount != null)
                ? offerDiscount.getProductOfferId()
                : null;
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
        basketRepository.saveBasket(basket.getBillingAccId(), basket);
    }

    @Override
    public void addByCampaignProduct(int billingAccountId, int campaignProductId) {
        var all = catalogServiceClient.getAllActiveCampaignProducts();
        var target = all.stream()
                .filter(cp -> cp.getCampaignProductId() == campaignProductId)
                .findFirst()
                .orElseThrow(() -> new BusinessException("Campaign product not found: " + campaignProductId));

        this.add(billingAccountId, target.getProductId());

        var basket = basketRepository.getBasketByBillingAccountId(billingAccountId);
        if (basket != null) { // 👈 basket null değilse meta yaz
            basket.setCampaignId(target.getCampaignId());
            basket.setCampaignName(target.getCampaignName());
            basketRepository.saveBasket(basket.getBillingAccId(), basket);
        }
    }


    @Override
    public void deleteItem(int billingAccountId, String basketItemId) {
        var basket = basketRepository.getBasketByBillingAccountId(billingAccountId);
        if (basket == null) return;

        boolean removed = basket.getBasketItems().removeIf(it -> it.getId().equals(basketItemId));
        if (!removed) {
            throw new BusinessException("Basket item not found with id: " + basketItemId);
        }

        recalcBasketTotal(basket);

        if (basket.getBasketItems().isEmpty()) {
            basketRepository.deleteBasket(billingAccountId);   // 👈 boşsa sil
        } else {
            basketRepository.saveBasket(billingAccountId, basket); // 👈 güncelle
        }
    }


    @Override
    public void clearBasket(int billingAccountId) {
        basketRepository.deleteBasket(billingAccountId);
    }
    // basketservice/service/concretes/BasketServiceImpl.java

    @Override
    public void addByCampaign(int billingAccountId, int campaignId) {
        var all = catalogServiceClient.getAllActiveCampaignProducts(); // tüm aktif campaign-product ilişkileri
        var itemsOfCampaign = all.stream()
                .filter(x -> x.getCampaignId() == campaignId)
                .toList();

        if (itemsOfCampaign.isEmpty()) {
            throw new BusinessException("No active items for campaignId=" + campaignId);
        }

        // Kampanyadaki TÜM productOffer’ları sepete ekle
        for (var cp : itemsOfCampaign) {
            this.add(billingAccountId, cp.getProductId());
        }

        // Sepet meta bilgisi (kampanya ismi/id)
        var basket = basketRepository.getBasketByBillingAccountId(billingAccountId);
        if (basket != null) {
            basket.setCampaignId(campaignId);
            // Aynı kampanya olduğundan ilk elemanın adı yeterli
            basket.setCampaignName(itemsOfCampaign.get(0).getCampaignName());
            basketRepository.saveBasket(basket.getBillingAccId(), basket);
        }
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

    @Override
    public Basket getByBillingAccountId(int billingAccountId) {
        // Repository'de bu metot zaten mevcuttu, onu çağırıyoruz.
        Basket basket = basketRepository.getBasketByBillingAccountId(billingAccountId);

        if (basket == null) {
            throw new BusinessException("Basket not found for billing account: " + billingAccountId);
        }

        return basket;
    }
}
