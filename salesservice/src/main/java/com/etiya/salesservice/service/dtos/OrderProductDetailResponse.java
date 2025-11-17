package com.etiya.salesservice.service.dtos;

import com.etiya.salesservice.domain.ProductConfiguration;

import java.util.List;

public class OrderProductDetailResponse {
    private String id;
    private String productOfferId;
    private String productOfferName;
    private String status;
    private int billingAccountId;
    private int addressId;
    private List<ProductConfiguration> configuration;

    public OrderProductDetailResponse() {}

    public OrderProductDetailResponse(String id, String productOfferId, String productOfferName,
                                      String status, int billingAccountId, int addressId,
                                      List<ProductConfiguration> configuration) {
        this.id = id;
        this.productOfferId = productOfferId;
        this.productOfferName = productOfferName;
        this.status = status;
        this.billingAccountId = billingAccountId;
        this.addressId = addressId;
        this.configuration = configuration;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBillingAccountId() {
        return billingAccountId;
    }

    public void setBillingAccountId(int billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public List<ProductConfiguration> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<ProductConfiguration> configuration) {
        this.configuration = configuration;
    }
}