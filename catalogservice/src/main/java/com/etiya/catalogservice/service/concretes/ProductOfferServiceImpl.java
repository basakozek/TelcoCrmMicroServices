package com.etiya.catalogservice.service.concretes;

import com.etiya.catalogservice.domain.entities.ProductOffer;
import com.etiya.catalogservice.domain.entities.ProductSpecification;
import com.etiya.catalogservice.repository.ProductOfferRepository;
import com.etiya.catalogservice.repository.ProductSpecificationRepository;
import com.etiya.catalogservice.service.abstracts.ProductOfferService;
import com.etiya.catalogservice.service.dtos.request.productOffer.CreateProductOfferRequest;
import com.etiya.catalogservice.service.dtos.response.productOffer.CreatedProductOfferResponse;
import com.etiya.catalogservice.service.dtos.response.productOffer.GetListProductOfferResponse;
import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.responses.ActiveProductOfferResponse;
import com.etiya.common.responses.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferServiceImpl implements ProductOfferService {

    private final ProductOfferRepository repository;
    private final ProductSpecificationRepository productSpecificationRepository;

    public ProductOfferServiceImpl(ProductOfferRepository repository, ProductSpecificationRepository productSpecificationRepository) {
        this.repository = repository;
        this.productSpecificationRepository = productSpecificationRepository;

    }

    @Override
    public Optional<ActiveProductOfferResponse> getBestActiveForProduct(String productId) {
        return repository.findBestActiveForProduct(productId).map(this::map);
    }

    @Override
    public List<ActiveProductOfferResponse> getAllActive() {
        return repository.findAllActive().stream().map(this::map).toList();
    }

    @Override
    public CreatedProductOfferResponse add(CreateProductOfferRequest r) {
        // 1) ProductSpecification kontrolü
        ProductSpecification spec = productSpecificationRepository.findById(r.getProductSpecificationId())
                .orElseThrow(() -> new BusinessException("ProductSpecification not found: " + r.getProductSpecificationId()));

        // 2) Tarih kuralı
        if (r.getEndDate() != null && r.getEndDate().isBefore(r.getStartDate())) {
            throw new BusinessException("endDate cannot be before startDate");
        }

        // 3) İndirim normalize (0..1’e)
        double rate = r.getDiscountRate();
        if (rate > 1.0) rate = rate / 100.0;
        if (rate < 0.0 || rate > 1.0) {
            throw new BusinessException("discountRate must be in [0,1] or [0,100]");
        }

        // 4) Kaydet
        ProductOffer po = new ProductOffer();
        po.setName(r.getName());
        po.setDescription(r.getDescription());
        po.setPrice(r.getPrice()); // YENİ
        po.setStock(r.getStock()); // YENİ
        po.setProductSpecification(spec); // YENİ
        po.setStartDate(r.getStartDate());
        po.setEndDate(r.getEndDate());
        po.setDiscountRate(rate);
        po.setStatus(r.getStatus());
        //po.setProduct(product);

        po = repository.save(po);

        // 5) Response
        CreatedProductOfferResponse resp = new CreatedProductOfferResponse();
        resp.setId(po.getId());
        resp.setName(po.getName());
        resp.setStock(po.getStock());
        resp.setStatus(po.getStatus());
        resp.setProductSpecificationId(po.getProductSpecification().getId()); // YENİ
        //resp.setProductId(product.getId());
        resp.setDiscountRate(po.getDiscountRate());
        resp.setPrice(po.getPrice());
        return resp;
    }

    @Override
    public List<GetListProductOfferResponse> getAll() {
        return List.of();
    }

    @Override
    public ProductResponse getByIdForBasket(String id) {
        ProductOffer offer = repository.findById(id)
                .orElseThrow(() -> new BusinessException("ProductOffer not found with id: " + id));

        ProductResponse response = new ProductResponse();
        response.setId(offer.getId());
        response.setProductName(offer.getName());
        response.setPrice(offer.getPrice()); // Liste fiyatını dönüyoruz
        return response;
    }


    private ActiveProductOfferResponse map(ProductOffer po) {
        ActiveProductOfferResponse r = new ActiveProductOfferResponse();
        r.setProductOfferId(po.getId());
        r.setProductId(po.getId()); // GÜNCELLENDİ (ProductOffer ID'si)
        r.setStatus(po.getStatus());
        r.setDescription(po.getDescription());
        r.setName(po.getName());
        r.setStartDate(po.getStartDate());
        r.setEndDate(po.getEndDate());
        // discountRate'i 0..1 normalize et
        double rate = po.getDiscountRate();
        if (rate > 1.0) rate = rate / 100.0;
        r.setDiscountRate(rate);
        return r;
    }

}
