package com.etiya.catalogservice.service.concretes;

import com.etiya.catalogservice.domain.entities.Catalog;
import com.etiya.catalogservice.repository.CatalogRepository;
import com.etiya.catalogservice.service.abstracts.CatalogService;
import com.etiya.catalogservice.service.dtos.request.catalog.CreateCatalogRequest;
import com.etiya.catalogservice.service.dtos.response.catalog.CreatedCatalogResponse;
import com.etiya.catalogservice.service.dtos.response.catalog.GetListCatalogResponse;
import com.etiya.catalogservice.service.mappers.CatalogMapper;
import com.etiya.catalogservice.service.rules.CatalogBusinessRules;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;
    private final CatalogBusinessRules catalogBusinessRules;

    public CatalogServiceImpl(CatalogRepository catalogRepository,CatalogBusinessRules catalogBusinessRules) {
        this.catalogRepository = catalogRepository;
        this.catalogBusinessRules = catalogBusinessRules;
    }

    @Override
    public CreatedCatalogResponse add(CreateCatalogRequest request) {
        catalogBusinessRules.checkIfParentCatalogExists(request.getParentId());

        Catalog catalog = CatalogMapper.INSTANCE.catalogFromCreateCatalogRequest(request);


        if (request.getParentId() != null) {
            Catalog parentRef = catalogRepository.getReferenceById(request.getParentId());
            catalog.setParent(parentRef);
        }

        Catalog savedCatalog = catalogRepository.save(catalog);

        CreatedCatalogResponse createdCatalogResponse = CatalogMapper.INSTANCE.createdCatalogResponseFromCatalog(savedCatalog);

        return createdCatalogResponse;
    }


    @Override
    public List<GetListCatalogResponse> getAll() {
        List<Catalog> catalogs = catalogRepository.findAll();
        List<GetListCatalogResponse> responseList = CatalogMapper.INSTANCE.getListCatalogResponseFromCatalog(catalogs);
        return responseList;
    }
}
