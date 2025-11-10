package com.etiya.catalogservice.service.dtos.response.catalogProductOffer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetListCatalogProductOfferResponse {
    private int id;
    private String productOfferId;
    private int catalogId;
}
