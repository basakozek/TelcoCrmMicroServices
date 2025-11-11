package com.etiya.basketservice.controller;

import com.etiya.basketservice.domain.Basket;
import com.etiya.basketservice.service.abstracts.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestParam int billingAccId,
                    @RequestParam String productOfferId,
                    @RequestParam(name = "qty", required = false, defaultValue = "1") int qty) {
        for (int i = 0; i < Math.max(1, qty); i++) {
            basketService.add(billingAccId, productOfferId);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Basket> getAll(){
        return basketService.getAll();
    }

    // YENİ: Sepetten tekil ürün silme
    @DeleteMapping("/{billingAccountId}/items/{basketItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteItem(@PathVariable int billingAccountId, @PathVariable String basketItemId) {
        basketService.deleteItem(billingAccountId, basketItemId);
    }

    // YENİ: Sepeti tamamen temizleme
    @DeleteMapping("/{billingAccountId}")
    @ResponseStatus(HttpStatus.OK)
    public void clearBasket(@PathVariable int billingAccountId) {
        basketService.clearBasket(billingAccountId);
    }
    @PostMapping("/campaign-products")
    @ResponseStatus(HttpStatus.CREATED)
    public void addByCampaignProduct(@RequestParam int billingAccId,
                                     @RequestParam int campaignProductId,
                                     @RequestParam(name = "qty", required = false, defaultValue = "1") int qty) {
        for (int i = 0; i < Math.max(1, qty); i++) {
            basketService.addByCampaignProduct(billingAccId, campaignProductId);
        }
    }

    // basketservice/controller/BasketController.java

    // import org.springframework.http.ResponseEntity;

    @GetMapping("/{billingAccountId}")
    public ResponseEntity<Basket> getByBillingAccount(@PathVariable int billingAccountId) {
        Basket b = basketService.getByBillingAccountId(billingAccountId);
        if (b == null) {
            return ResponseEntity.noContent().build(); // 204, body yok
        }
        return ResponseEntity.ok(b); // 200
    }



    // basketservice/controller/BasketController.java

    @PostMapping("/campaigns")
    @ResponseStatus(HttpStatus.CREATED)
    public void addByCampaign(@RequestParam int billingAccId,
                              @RequestParam int campaignId) {
        basketService.addByCampaign(billingAccId, campaignId);
    }
}
