package com.etiya.catalogservice.service.abstracts;

import com.etiya.catalogservice.service.dtos.request.catalog.CreateCatalogRequest;
import com.etiya.catalogservice.service.dtos.response.catalog.CreatedCatalogResponse;
import com.etiya.catalogservice.service.dtos.response.catalog.GetListCatalogResponse;

import java.util.List;

public interface CatalogService {

    CreatedCatalogResponse add(CreateCatalogRequest request);

    List<GetListCatalogResponse> getAll();
}
