package com.etiya.basketservice.controller;

import com.etiya.basketservice.domain.Basket;
import com.etiya.basketservice.service.abstracts.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/by-billing/{billingAccountId}")
    @ResponseStatus(HttpStatus.OK)
    public Basket getByBillingAccountId(@PathVariable int billingAccountId) {
        return basketService.getByBillingAccountId(billingAccountId);
    }
}
