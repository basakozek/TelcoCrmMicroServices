package com.etiya.catalogservice.service.dtos.response.productConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfigCharacteristicResponse {

    private String key;             // "Pstn No", "XDSL User Name" ...
    private String dataType;        // Characteristic.dataType
    private String unitOfMeasure;   // Characteristic.unitOfMeasure
    private boolean required;       // ProductSpecCharacteristic.isRequired

    private List<String> allowedValues; // CharacteristicValue.value listesi
    private String defaultValue;        // ProdOfferCharValues üzerinden gelen değer (varsa)
}
