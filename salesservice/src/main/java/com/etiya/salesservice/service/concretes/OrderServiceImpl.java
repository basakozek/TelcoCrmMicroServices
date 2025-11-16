package com.etiya.salesservice.service.concretes;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.events.ClearBasketEvent;
import com.etiya.salesservice.client.BasketServiceClient;
import com.etiya.salesservice.domain.Order;
import com.etiya.salesservice.domain.OrderItem;
import com.etiya.salesservice.domain.OrderProduct;
import com.etiya.salesservice.domain.ProductConfiguration;
import com.etiya.salesservice.repository.CustomerProductRepository;
import com.etiya.salesservice.repository.OrderRepository;
import com.etiya.salesservice.service.abstracts.OrderService;
import com.etiya.salesservice.service.dtos.*;
import com.etiya.salesservice.transport.kafka.producer.ClearBasketProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerProductRepository customerProductRepository;
    private final BasketServiceClient basketServiceClient;
    private final ClearBasketProducer clearBasketProducer;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerProductRepository customerProductRepository, BasketServiceClient basketServiceClient, ClearBasketProducer clearBasketProducer) {
        this.orderRepository = orderRepository;
        this.customerProductRepository = customerProductRepository;
        this.basketServiceClient = basketServiceClient;
        this.clearBasketProducer = clearBasketProducer;
    }

    @Override
    @Transactional
    public void add(CreateOrderRequest request) {


        //TODO 1: (Sepeti Feign ile Çek) ---
        BasketDTO basket = basketServiceClient.getByBillingAccountId(request.getBillingAccountId());

        // Sepet kontrolleri
        if (basket == null || basket.getBasketItems() == null || basket.getBasketItems().isEmpty()) {
            throw new BusinessException("Cannot create order with an empty basket for billing account: " + request.getBillingAccountId());
        }
        // --- 2. ADIM: Tarihsel Siparişi (Order) Oluştur ---
        Order order = new Order();
        order.setBillingAccId(String.valueOf(basket.getBillingAccId()));
        order.setTotalPrice(basket.getTotalPrice());
        order.setAddressId(request.getAddressId());

        // Sepet kalemlerini sipariş kalemlerine (OrderItem) dönüştür
        List<OrderItem> orderItems = basket.getBasketItems().stream()
                .map(itemDTO -> new OrderItem(
                        itemDTO.getId(),          // BasketItem'ın kendi ID'si
                        itemDTO.getProductId(),   // Bu, ProductOffer ID'sidir
                        itemDTO.getProductName(),
                        // Sepetteki indirimli fiyatı ve adedi hesaba kat
                        (itemDTO.getProductPrice() * (1 - itemDTO.getDiscount())) * itemDTO.getQuantity()
                ))
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        orderRepository.save(order); // Siparişin 'fişi' MongoDB'ye kaydedildi.

        // --- 3. ADIM: Aktif Müşteri Ürünlerini (CustomerProduct) Oluştur ---

        // Konfigürasyonları (XDSL No, Modem SN vb.) kolay erişim için bir Map'e dönüştür
        Map<String, List<ProductConfiguration>> configMap = request.getConfigurations().stream()
                .collect(Collectors.toMap(
                        // Key: ProductOfferId (örn: "71233")
                        confDTO -> confDTO.getProductOfferId(),
                        // Value: Konfigürasyon listesi (örn: [ {key:"XDSL No", value:"123"} ])
                        confDTO -> confDTO.getConfiguration().stream()
                                .map(dtoPair -> new ProductConfiguration(dtoPair.getKey(), dtoPair.getValue())) // DTO -> Domain'e çevir
                                .collect(Collectors.toList())
                ));

        // Sepetteki her bir *ürün kalemi* için bir *Müşteri Varlığı* oluştur
        for (BasketItemDTO item : basket.getBasketItems()) {
            // (Sepette aynı üründen 3 tane varsa, 3 ayrı CustomerProduct oluşturmak yerine
            // quantity=3 olarak tek bir CustomerProduct da oluşturulabilir.
            // Şimdilik, her sepet kalemi için 1 ürün varsayıyoruz, bu daha basit.)
            OrderProduct product = new OrderProduct();
            product.setBillingAccountId(request.getBillingAccountId());
            product.setProductOfferId(item.getProductId()); // "71233", "202610" vs.
            product.setProductOfferName(item.getProductName());
            product.setStatus("Active"); // Veya aktivasyon gerekiyorsa "Pending_Activation"
            product.setAddressId(request.getAddressId());

            // Ürüne ait (DOMAINE çevrilmiş) konfigürasyon listesini Map'ten al
            List<ProductConfiguration> configs = configMap.get(item.getProductId());

            if (configs != null && !configs.isEmpty()) {
                product.setConfiguration(configs);
            }

            customerProductRepository.save(product); // Aktif varlık MongoDB'ye kaydedildi.
        }

        // --- 4. ADIM: TODO 2'yi ÇÖZ (Sepeti Temizle Event'i Fırlat) ---
        clearBasketProducer.produceClearBasketEvent(new ClearBasketEvent(request.getBillingAccountId()));
    }

    @Override
    public List<BillingAccountProductResponse> getProductsForBillingAccount(int billingAccountId) {

        List<OrderProduct> products = customerProductRepository.findByBillingAccountId(billingAccountId);

        // 2. DTO mapping
        return products.stream()
                .map(product -> new BillingAccountProductResponse(
                        product.getProductOfferId(),
                        product.getProductOfferName(),
                        product.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteProduct(String productId) {

        OrderProduct product = customerProductRepository.findByProductOfferId(productId)
                .orElseThrow(() -> new BusinessException("Product not found with id: " + productId));
        if (!product.getStatus().equals("Active")) {
            throw new BusinessException("Only active products can be deleted.");
        }
        customerProductRepository.deleteById(product.getId());
    }




    // TODO: Bu alanda basketservice tarafına istek atılıp sepetteki veriyi sişariş tarafına göndermek
        // => basketServiceClient.getByBillingAccoubtId(billingAccountId)

        // TODO: Sipariş onaylandıktan sonra basket service taradına sepetin boşaltılması için event fırlatılacak
        // orderRepository.save(order) dediktan sonra
        // var basketClearEvent = new BasketClearEvent(order.BillingAccountId) (basketId de olabilir. pairde konuşalım)
        // producer.send(basketClearEvent)

}

