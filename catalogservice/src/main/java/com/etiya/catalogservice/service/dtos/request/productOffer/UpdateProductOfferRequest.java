package com.etiya.catalogservice.service.dtos.request.productOffer;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductOfferRequest {

    @NotEmpty(message = "Product name cannot be empty.")
    @Size(min = 2, max = 100)
    private String name;

    private String description;

    // Offer'ın kendi indirim alanları
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Min(value = 0, message = "Discount rate cannot be negative")
    private double discountRate;

    @NotEmpty(message = "Status cannot be empty")
    private String status;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private double price;

    @Min(value = 0, message = "Stock cannot be negative.")
    private int stock;

}
