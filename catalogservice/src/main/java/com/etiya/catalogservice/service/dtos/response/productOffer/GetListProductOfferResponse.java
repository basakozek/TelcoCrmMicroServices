package com.etiya.catalogservice.service.dtos.response.productOffer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListProductOfferResponse {
    private String id; // UUID
    private String name;
    private double price; // Liste fiyatı
    private int stock;
    private String status;
    private String productSpecificationName; // Spec'in adı
}
