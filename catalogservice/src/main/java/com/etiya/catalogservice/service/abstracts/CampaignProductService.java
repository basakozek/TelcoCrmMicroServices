package com.etiya.catalogservice.service.abstracts;

import com.etiya.catalogservice.service.dtos.request.campaignProduct.CreateCampaignProductRequest;
import com.etiya.catalogservice.service.dtos.response.campaignProduct.CreatedCampaignProductResponse;
import com.etiya.common.responses.ActiveCampaignProductResponse;

import java.util.List;
import java.util.Optional;

public interface CampaignProductService {

    Optional<ActiveCampaignProductResponse> getBestActiveForProduct(String productId);
    List<ActiveCampaignProductResponse> getAllActive();
    CreatedCampaignProductResponse add(CreateCampaignProductRequest request);
}
