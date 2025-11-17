package com.etiya.salesservice.service.dtos;

import com.etiya.salesservice.domain.ProductConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDetailResponse {
    private String id;
    private String productOfferId;
    private String productOfferName;
    private String status;
    private int billingAccountId;
    private int addressId;
    private List<ProductConfiguration> configuration;

}