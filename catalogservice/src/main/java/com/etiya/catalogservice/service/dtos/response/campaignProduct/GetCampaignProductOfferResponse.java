package com.etiya.catalogservice.service.dtos.response.campaignProduct;

public class GetCampaignProductOfferResponse {
    private int campaignId;
    private String productOfferId;
    private String productOfferName;

    public GetCampaignProductOfferResponse() {}

    public GetCampaignProductOfferResponse(int campaignId, String productOfferId, String productOfferName) {
        this.campaignId = campaignId;
        this.productOfferId = productOfferId;
        this.productOfferName = productOfferName;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getProductOfferId() {
        return productOfferId;
    }

    public void setProductOfferId(String productOfferId) {
        this.productOfferId = productOfferId;
    }

    public String getProductOfferName() {
        return productOfferName;
    }

    public void setProductOfferName(String productOfferName) {
        this.productOfferName = productOfferName;
    }
}
