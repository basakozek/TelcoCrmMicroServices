package com.etiya.basketservice.domain;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Basket implements Serializable {

    private String id;

    private int billingAccId ; // billing account id'si kullanılacak

    private double totalPrice;

    private Integer campaignId;
    private String campaignName;

    private List<BasketItem> basketItems;

    public Basket(){
        this.id = UUID.randomUUID().toString();
        this.basketItems = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBillingAccId() {
        return billingAccId;
    }

    public void setBillingAccId(int billingAccId) {
        this.billingAccId = billingAccId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
}
