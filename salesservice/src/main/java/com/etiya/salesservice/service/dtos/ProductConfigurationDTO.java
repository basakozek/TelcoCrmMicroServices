package com.etiya.salesservice.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//ADIM: Gelen konfigürasyon listesinin iç yapısı
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfigurationDTO {
    private String productOfferId; // Hangi ürüne ait
    private List<ConfigurationPair> configuration; // Key-Value listesi
}
