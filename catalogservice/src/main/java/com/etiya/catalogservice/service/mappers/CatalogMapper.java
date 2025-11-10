package com.etiya.catalogservice.service.mappers;

import com.etiya.catalogservice.domain.entities.Catalog;
import com.etiya.catalogservice.service.dtos.request.catalog.CreateCatalogRequest;
import com.etiya.catalogservice.service.dtos.response.catalog.CreatedCatalogResponse;
import com.etiya.catalogservice.service.dtos.response.catalog.GetListCatalogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CatalogMapper {
    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    //create

    Catalog catalogFromCreateCatalogRequest(CreateCatalogRequest request);
    @Mapping(target = "parentId", source = "parent.id") // Tersine FK mapping
    CreatedCatalogResponse createdCatalogResponseFromCatalog(Catalog catalog);


    @Mapping(target = "parentId", source = "parent.id")
    GetListCatalogResponse getListCatalogResponseFromCatalog(Catalog catalog);

    List<GetListCatalogResponse> getListCatalogResponseFromCatalog(List<Catalog> catalogs);

}
