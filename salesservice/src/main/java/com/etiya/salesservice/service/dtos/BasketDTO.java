package com.etiya.salesservice.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Bu, basketservice'ten Feign ile çekeceğimiz sepetin yapısıdır
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDTO {
    private String id;
    private int billingAccId;
    private double totalPrice;
    private List<BasketItemDTO> basketItems;
}