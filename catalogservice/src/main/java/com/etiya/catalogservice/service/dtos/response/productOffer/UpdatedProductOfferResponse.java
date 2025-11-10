package com.etiya.catalogservice.service.dtos.response.productOffer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedProductOfferResponse {
    private String id; // UUID
    private String name;
    private double price; // Liste fiyatı
    private int stock;
    private int productSpecificationId;
    private String status;
    private double discountRate;
    private LocalDateTime updatedDate;
}
