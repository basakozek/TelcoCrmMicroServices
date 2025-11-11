package com.etiya.basketservice.service.abstracts;

import com.etiya.basketservice.domain.Basket;

import java.util.Map;

public interface BasketService {
    void add(int billingAccountId, String productOfferId);
    void addByCampaignProduct(int billingAccountId, int campaignProductId);
    // YENİ: Sepetten tek bir kalemi siler (BasketItem'ın kendi ID'si ile)
    void deleteItem(int billingAccountId, String basketItemId);

    // YENİ: Belirli bir billingAccount'a ait sepeti temizler
    void clearBasket(int billingAccountId);
    Basket getByBillingAccountId(int billingAccountId);
    void addByCampaign(int billingAccountId, int campaignId);
    Map<String, Basket> getAll();

}
