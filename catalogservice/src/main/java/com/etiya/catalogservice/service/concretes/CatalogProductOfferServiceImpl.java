package com.etiya.catalogservice.service.concretes;

import com.etiya.catalogservice.domain.entities.CatalogProductOffer;
import com.etiya.catalogservice.repository.CatalogProductOfferRepository;
import com.etiya.catalogservice.repository.CatalogRepository;
import com.etiya.catalogservice.service.abstracts.CatalogProductOfferService;
import com.etiya.catalogservice.service.dtos.response.catalogProductOffer.CatalogProductOfferWithDetailResponse;
import com.etiya.catalogservice.service.dtos.response.catalogProductOffer.GetListCatalogProductOfferResponse;
import com.etiya.catalogservice.service.mappers.CatalogProductOfferMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogProductOfferServiceImpl implements CatalogProductOfferService {

    private final CatalogProductOfferRepository catalogProductOfferRepository;
    private final CatalogRepository catalogRepository;

    public CatalogProductOfferServiceImpl(CatalogProductOfferRepository catalogProductOfferRepository, CatalogRepository catalogRepository) {
        this.catalogProductOfferRepository = catalogProductOfferRepository;
        this.catalogRepository = catalogRepository;
    }


    @Override
    public List<GetListCatalogProductOfferResponse> getAll() {
        List<CatalogProductOffer> cpos = catalogProductOfferRepository.findAll();
        return CatalogProductOfferMapper.INSTANCE
                .getListCatalogProductOfferResponseFromCatalogProductOffer(cpos);
    }

    @Override
    public List<CatalogProductOfferWithDetailResponse> getByCatalogId(int catalogId) {
        // Eski davranış (tek catalog)
        var cpos = catalogProductOfferRepository.findAllByCatalogIdsWithDetail(List.of(catalogId));
        return CatalogProductOfferMapper.INSTANCE.toResponse(cpos);
    }

    // YENİ: includeChildren
    public List<CatalogProductOfferWithDetailResponse> getByCatalogId(int catalogId, boolean includeChildren) {
        List<Integer> ids = includeChildren
                ? catalogRepository.findSubtreeIds(catalogId)
                : List.of(catalogId);

        var cpos = catalogProductOfferRepository.findAllByCatalogIdsWithDetail(ids);
        return CatalogProductOfferMapper.INSTANCE.toResponse(cpos);
    }

    @Override
    public List<CatalogProductOfferWithDetailResponse> getActiveByCatalogId(int catalogId) {
        var cpos = catalogProductOfferRepository.findActiveByCatalogIdWithDetail(catalogId, java.time.LocalDateTime.now());
        return CatalogProductOfferMapper.INSTANCE.toResponse(cpos);
    }

    // YENİ: aktif + includeChildren
    public List<CatalogProductOfferWithDetailResponse> getActiveByCatalogId(int catalogId, boolean includeChildren) {
        List<Integer> ids = includeChildren
                ? catalogRepository.findSubtreeIds(catalogId)
                : List.of(catalogId);

        var cpos = catalogProductOfferRepository.findActiveByCatalogIdsWithDetail(ids, java.time.LocalDateTime.now());
        return CatalogProductOfferMapper.INSTANCE.toResponse(cpos);
    }
}
