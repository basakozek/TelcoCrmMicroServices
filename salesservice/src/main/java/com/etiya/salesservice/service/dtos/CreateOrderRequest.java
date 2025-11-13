package com.etiya.salesservice.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//API'dan alınacak ana request DTO'su
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private int billingAccountId;
    private List<ProductConfigurationDTO> configurations;
    private int addressId;
}
