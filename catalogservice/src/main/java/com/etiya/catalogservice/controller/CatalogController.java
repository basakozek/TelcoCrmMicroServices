package com.etiya.catalogservice.controller;

import com.etiya.catalogservice.service.abstracts.CatalogService;
import com.etiya.catalogservice.service.dtos.request.catalog.CreateCatalogRequest;

import com.etiya.catalogservice.service.dtos.response.catalog.CreatedCatalogResponse;
import com.etiya.catalogservice.service.dtos.response.catalog.GetListCatalogResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogs/")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedCatalogResponse addCatalog(@Valid @RequestBody CreateCatalogRequest createCatalogRequest) {
        return catalogService.add(createCatalogRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetListCatalogResponse> getAll(){
        return catalogService.getAll();
    }
}
