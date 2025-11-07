package com.etiya.common.responses;


import java.time.LocalDateTime;

public class ActiveProductOfferResponse {
    private int productOfferId;
    private String productId;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double discountRate; // 0..1 (yüzde geldiyse servis 0..1'e çevirir)
    private String status; // "Active" vs.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getProductOfferId() {
        return productOfferId;
    }

    public void setProductOfferId(int productOfferId) {
        this.productOfferId = productOfferId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
