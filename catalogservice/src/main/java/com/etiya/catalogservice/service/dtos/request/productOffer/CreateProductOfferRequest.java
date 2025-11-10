package com.etiya.catalogservice.service.dtos.request.productOffer;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductOfferRequest {

    @NotEmpty(message = "Product name cannot be empty.")
    @Size(min = 2, max = 100)
    private String name;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate; // null olabilir

    private double discountRate; // 0..1 ya da yüzde (örn 15). Service normalize eder.

    private String status; // örn: "Active"

    // YENİ ALANLAR (Eski Product'tan)
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", message = "Price must be greater than 0")
    private double price; // Liste fiyatı

    @NotNull(message = "Stock cannot be null")
    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    @NotNull(message = "Product Specification ID cannot be null")
    private int productSpecificationId; // spec_id (int)

}
