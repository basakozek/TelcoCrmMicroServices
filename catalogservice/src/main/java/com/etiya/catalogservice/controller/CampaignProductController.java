package com.etiya.catalogservice.controller;

import com.etiya.catalogservice.service.abstracts.CampaignProductOfferService;
import com.etiya.catalogservice.service.dtos.request.campaignProduct.CreateCampaignProductRequest;
import com.etiya.catalogservice.service.dtos.response.campaignProduct.CreatedCampaignProductResponse;
import com.etiya.catalogservice.service.dtos.response.campaignProduct.GetCampaignProductOfferResponse;
import com.etiya.common.responses.ActiveCampaignProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/campaign-products/")
public class CampaignProductController {
    private final CampaignProductOfferService service;

    public CampaignProductController(CampaignProductOfferService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedCampaignProductResponse add(@Valid @RequestBody CreateCampaignProductRequest request) {
        return service.add(request);
    }

    // Liste: /api/campaign-products/active
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<ActiveCampaignProductResponse> getAllActive() {
        return service.getAllActive();
    }

    // Ürüne göre en iyi aktif kampanya: /api/campaign-products/active/{productId}
    @GetMapping("/active/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ActiveCampaignProductResponse getBestActiveForProduct(@PathVariable String productId) {
        return service.getBestActiveForProduct(productId)
                .orElseGet(() -> {
                    var r = new ActiveCampaignProductResponse();
                    r.setCampaignProductId(0);
                    r.setCampaignId(0);
                    r.setCampaignName("None");
                    r.setProductId(productId);
                    r.setDiscountRate(0.0);
                    return r;
                });
    }

    @GetMapping("/search/by-id")
    public List<GetCampaignProductOfferResponse> searchByCampaignId(@RequestParam int campaignId) {
        return service.searchByCampaignId(campaignId);
    }

    @GetMapping("/search/by-name")
    public List<GetCampaignProductOfferResponse> searchByCampaignName(@RequestParam String campaignName) {
        return service.searchByCampaignName(campaignName);
    }

}
