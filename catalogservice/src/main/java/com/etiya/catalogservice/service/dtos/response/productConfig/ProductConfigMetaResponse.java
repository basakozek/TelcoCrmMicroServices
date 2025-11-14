package com.etiya.catalogservice.service.dtos.response.productConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfigMetaResponse {

    private String productOfferId;
    private String productOfferName;

    private List<ProductConfigCharacteristicResponse> characteristics;
}