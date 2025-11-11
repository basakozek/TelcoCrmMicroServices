package com.etiya.salesservice.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemDTO {
    private String id; // BasketItem'ın kendi ID'si
    private String productId; // Bu, Catalog'daki ProductOffer ID'sidir
    private String productName;
    private double productPrice; // Liste fiyatı
    private double discount; // 0..1 arası
    private int quantity;

    // public double getDiscountedPrice() { ... } // Gerekirse hesaplama eklenebilir
}
