package com.etiya.catalogservice.controller;

import com.etiya.catalogservice.service.abstracts.CatalogProductOfferService;
import com.etiya.catalogservice.service.dtos.response.catalogProductOffer.CatalogProductOfferWithDetailResponse;
import com.etiya.catalogservice.service.dtos.response.catalogProductOffer.GetListCatalogProductOfferResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// com.etiya.catalogservice.controller
@RestController
@RequestMapping("/api/catalog-product-offers")
public class CatalogProductOfferController {

    private final CatalogProductOfferService catalogProductOfferService;

    public CatalogProductOfferController(CatalogProductOfferService catalogProductOfferService) {
        this.catalogProductOfferService = catalogProductOfferService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetListCatalogProductOfferResponse> getListCatalogProductOffers() {
        return catalogProductOfferService.getAll();
    }

    // /api/catalog-product-offers/by-catalog/42
    @GetMapping("/by-catalog/{catalogId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CatalogProductOfferWithDetailResponse> getByCatalog(@PathVariable int catalogId) {
        return catalogProductOfferService.getByCatalogId(catalogId);
    }

    // /api/catalog-product-offers/by-catalog/42/active
    @GetMapping("/by-catalog/{catalogId}/active")
    @ResponseStatus(HttpStatus.OK)
    public List<CatalogProductOfferWithDetailResponse> getActiveByCatalog(@PathVariable int catalogId) {
        return catalogProductOfferService.getActiveByCatalogId(catalogId);
    }
}

