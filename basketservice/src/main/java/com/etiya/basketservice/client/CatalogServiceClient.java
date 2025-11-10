package com.etiya.basketservice.client;


import com.etiya.common.responses.ActiveCampaignProductResponse;
import com.etiya.common.responses.ActiveProductOfferResponse;
import com.etiya.common.responses.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "catalogservice"
)
public interface CatalogServiceClient {

    @GetMapping("/api/product-offers/{id}/for-basket")
    ProductResponse getById(@PathVariable("id") String id);

    // GÜNCELLENDİ: Ürünün kendi indirimini çeker.
    @GetMapping("/api/product-offers/active/{productOfferId}")
    ActiveProductOfferResponse getBestActiveOffer(@PathVariable("productOfferId") String productOfferId);

    // Ürüne bağlı en iyi aktif kampanya
    @GetMapping("/api/campaign-products/active/{productOfferId}")
    ActiveCampaignProductResponse getBestActiveCampaign(@PathVariable("productOfferId") String productOfferId);

}
