package com.etiya.basketservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class BasketItem implements Serializable {

    private String id;

    private String productId;

    private String productName;

    private double productPrice;// BigDecimal olacak

    private double discount;

    private int quantity;

    private double discountedPrice;

    private String productOfferId;

    private int campaignProductId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscountedPrice() {
        // Güvenli tarafta kal: her okumada dinamik hesap
        double rate = discount;
        if (rate < 0) rate = 0;
        if (rate > 1) rate = 1;
        return productPrice * (1 - rate);
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getProductOfferId() {
        return productOfferId;
    }

    public void setProductOfferId(String productOfferId) {
        this.productOfferId = productOfferId;
    }

    public int getCampaignProductId() {
        return campaignProductId;
    }

    public void setCampaignProductId(int campaignProductId) {
        this.campaignProductId = campaignProductId;
    }

    public BasketItem(){
        this.id = UUID.randomUUID().toString();
    }


}
