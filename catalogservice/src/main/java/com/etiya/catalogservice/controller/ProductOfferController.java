package com.etiya.catalogservice.controller;

import com.etiya.catalogservice.service.abstracts.ProductOfferService;
import com.etiya.catalogservice.service.dtos.request.productOffer.CreateProductOfferRequest;
import com.etiya.catalogservice.service.dtos.response.productOffer.CreatedProductOfferResponse;
import com.etiya.common.responses.ActiveProductOfferResponse;
import com.etiya.common.responses.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-offers/")
public class ProductOfferController {

    private final ProductOfferService service;

    public ProductOfferController(ProductOfferService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedProductOfferResponse add(@Valid @RequestBody CreateProductOfferRequest request) {
        return service.add(request);
    }

    // Liste: /api/product-offers/active
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<ActiveProductOfferResponse> getAllActive() {
        return service.getAllActive();
    }

    // Ürüne göre en iyi aktif teklif: /api/product-offers/active/{productId}
    @GetMapping("/active/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ActiveProductOfferResponse getBestActiveForProduct(@PathVariable String productId) {
        return service.getBestActiveForProduct(productId)
                .orElseGet(() -> {
                    var r = new ActiveProductOfferResponse();
                    r.setProductOfferId("0");
                    r.setProductId(productId);
                    r.setDiscountRate(0.0);
                    r.setStatus("None");
                    return r;
                });
    }
    // YENİ ENDPOINT (BasketService Feign Client için)
    @GetMapping("/{id}/for-basket")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getByIdForBasket(@PathVariable String id) {
        return service.getByIdForBasket(id);
    }

    // NOT: getList, getById, update, delete endpoint'lerini de buraya ekleyebiliriz

}
