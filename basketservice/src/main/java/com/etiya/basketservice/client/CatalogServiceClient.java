package com.etiya.basketservice.client;


import com.etiya.common.responses.ActiveCampaignProductResponse;
import com.etiya.common.responses.ActiveProductOfferResponse;
import com.etiya.common.responses.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// basketservice/client/CatalogServiceClient.java
@FeignClient(name = "catalogservice")
public interface CatalogServiceClient {

    @GetMapping("/api/product-offers/{id}/for-basket")
    ProductResponse getById(@PathVariable("id") String id);

    @GetMapping("/api/product-offers/active/{productOfferId}")
    ActiveProductOfferResponse getBestActiveOffer(@PathVariable("productOfferId") String productOfferId);

    @GetMapping("/api/campaign-products/active/{productOfferId}")
    ActiveCampaignProductResponse getBestActiveCampaign(@PathVariable("productOfferId") String productOfferId);

    // ✅ addByCampaignProduct için şart
    @GetMapping("/api/campaign-products/active")
    java.util.List<ActiveCampaignProductResponse> getAllActiveCampaignProducts();
}

