package com.etiya.catalogservice.service.mappers;

import com.etiya.catalogservice.domain.entities.CatalogProductOffer;
import com.etiya.catalogservice.service.dtos.response.catalog.GetListCatalogResponse;
import com.etiya.catalogservice.service.dtos.response.catalogProductOffer.CatalogProductOfferWithDetailResponse;
import com.etiya.catalogservice.service.dtos.response.catalogProductOffer.GetListCatalogProductOfferResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CatalogProductOfferMapper {

    CatalogProductOfferMapper INSTANCE = Mappers.getMapper(CatalogProductOfferMapper.class);

    // --- Mevcut basit liste (id’ler) ---
    @Mapping(target = "productOfferId", source = "productOffer.id")
    @Mapping(target = "catalogId", source = "catalog.id")
    GetListCatalogProductOfferResponse getListCatalogProductOfferResponseFromCatalogProductOffer(CatalogProductOffer cpo);
    List<GetListCatalogProductOfferResponse> getListCatalogProductOfferResponseFromCatalogProductOffer(List<CatalogProductOffer> cpos);

    // --- Detaylı response (FE’nin kullandığı) ---
    @Mapping(target = "catalogProductOfferId", source = "id")
    @Mapping(target = "catalogId",           source = "catalog.id")

    @Mapping(target = "productOfferId",        source = "productOffer.id")
    @Mapping(target = "productOfferName",      source = "productOffer.name")
    @Mapping(target = "productOfferDescription", source = "productOffer.description")

    @Mapping(target = "discountRate",          source = "productOffer.discountRate")
    @Mapping(target = "status",                source = "productOffer.status")
    @Mapping(target = "startDate",             source = "productOffer.startDate")
    @Mapping(target = "endDate",               source = "productOffer.endDate")

    @Mapping(target = "productId",    source = "productOffer.id")
    @Mapping(target = "productName",  source = "productOffer.name")
    @Mapping(target = "productPrice", source = "productOffer.price")
    CatalogProductOfferWithDetailResponse toResponse(CatalogProductOffer cpo);

    List<CatalogProductOfferWithDetailResponse> toResponse(List<CatalogProductOffer> cpos);

    @AfterMapping
    default void normalizeRate(@MappingTarget CatalogProductOfferWithDetailResponse r) {
        double rate = r.getDiscountRate();
        if (rate > 1.0) rate = rate / 100.0;
        r.setDiscountRate(rate);
    }
}
