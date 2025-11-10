package com.etiya.catalogservice.service.concretes;

import com.etiya.catalogservice.domain.entities.CatalogProductOffer;
import com.etiya.catalogservice.repository.CatalogProductOfferRepository;
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

    public CatalogProductOfferServiceImpl(CatalogProductOfferRepository catalogProductOfferRepository) {
        this.catalogProductOfferRepository = catalogProductOfferRepository;
    }


    @Override
    public List<GetListCatalogProductOfferResponse> getAll() {
        List<CatalogProductOffer> cpos = catalogProductOfferRepository.findAll();
        return CatalogProductOfferMapper.INSTANCE
                .getListCatalogProductOfferResponseFromCatalogProductOffer(cpos);
    }

    @Override
    public List<CatalogProductOfferWithDetailResponse> getByCatalogId(int catalogId) {
        var cpos = catalogProductOfferRepository.findAllByCatalogIdWithDetail(catalogId);
        return CatalogProductOfferMapper.INSTANCE.toResponse(cpos);
    }

    @Override
    public List<CatalogProductOfferWithDetailResponse> getActiveByCatalogId(int catalogId) {
        var cpos = catalogProductOfferRepository.findActiveByCatalogIdWithDetail(catalogId, LocalDateTime.now());
        return CatalogProductOfferMapper.INSTANCE.toResponse(cpos);
    }
}
