package com.etiya.basketservice.service.abstracts;

import com.etiya.basketservice.domain.Basket;

import java.util.Map;

public interface BasketService {
    void add(int billingAccountId, String productOfferId);

    // YENİ: Sepetten tek bir kalemi siler (BasketItem'ın kendi ID'si ile)
    void deleteItem(int billingAccountId, String basketItemId);

    // YENİ: Belirli bir billingAccount'a ait sepeti temizler
    void clearBasket(int billingAccountId);

    Map<String, Basket> getAll();
}
