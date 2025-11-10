package com.etiya.catalogservice.service.abstracts;

import com.etiya.catalogservice.service.dtos.response.catalog.GetListCatalogResponse;
import com.etiya.catalogservice.service.dtos.response.catalogProductOffer.CatalogProductOfferWithDetailResponse;
import com.etiya.catalogservice.service.dtos.response.catalogProductOffer.GetListCatalogProductOfferResponse;

import java.util.List;

public interface CatalogProductOfferService {
    List<GetListCatalogProductOfferResponse> getAll();

    List<CatalogProductOfferWithDetailResponse> getByCatalogId(int catalogId);
    List<CatalogProductOfferWithDetailResponse> getActiveByCatalogId(int catalogId);
}
