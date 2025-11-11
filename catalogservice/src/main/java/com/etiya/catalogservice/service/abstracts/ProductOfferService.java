package com.etiya.catalogservice.service.abstracts;

import com.etiya.catalogservice.service.dtos.request.productOffer.CreateProductOfferRequest;
import com.etiya.catalogservice.service.dtos.response.productOffer.CreatedProductOfferResponse;
import com.etiya.catalogservice.service.dtos.response.productOffer.GetListProductOfferResponse;
import com.etiya.catalogservice.service.dtos.response.productOffer.GetListSearchProductOfferResponse;
import com.etiya.common.responses.ActiveProductOfferResponse;
import com.etiya.common.responses.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductOfferService {
    Optional<ActiveProductOfferResponse> getBestActiveForProduct(String productOfferId);
    List<ActiveProductOfferResponse> getAllActive();

    CreatedProductOfferResponse add(CreateProductOfferRequest request);

    List<GetListProductOfferResponse> getAll();

    // YENİ METOT (BasketService'in Feign Client'ı için)
    ProductResponse getByIdForBasket(String id);

    List<GetListSearchProductOfferResponse> searchById(String id);
    List<GetListSearchProductOfferResponse> searchByName(String name);
}
