package com.etiya.catalogservice.service.dtos.request.campaignProduct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCampaignProductRequest {
    private String productId;
    private Integer campaignId;
}
