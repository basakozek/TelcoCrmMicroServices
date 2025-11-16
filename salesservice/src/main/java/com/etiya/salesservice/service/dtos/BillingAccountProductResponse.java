package com.etiya.salesservice.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingAccountProductResponse {
    private String productOfferId;
    private String productOfferName;
    private String status;
}