package com.etiya.common.responses;

public class ActiveCampaignProductResponse {
    private int campaignProductId;
    private int campaignId;
    private String campaignName;
    private String productOfferId;
    private double discountRate; // 0..1


    public int getCampaignProductId() {
        return campaignProductId;
    }

    public void setCampaignProductId(int campaignProductId) {
        this.campaignProductId = campaignProductId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getProductId() {
        return productOfferId;
    }

    public void setProductId(String productId) {
        this.productOfferId = productId;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}
