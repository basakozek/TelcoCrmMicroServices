package com.etiya.catalogservice.service.abstracts;

import com.etiya.catalogservice.service.dtos.response.productConfig.ProductConfigMetaResponse;

public interface ProductConfigMetaService {

    ProductConfigMetaResponse getConfigMeta(String productOfferId);
}

