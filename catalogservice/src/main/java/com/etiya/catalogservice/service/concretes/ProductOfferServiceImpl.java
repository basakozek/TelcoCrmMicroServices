package com.etiya.catalogservice.service.concretes;

import com.etiya.catalogservice.domain.entities.Product;
import com.etiya.catalogservice.domain.entities.ProductOffer;
import com.etiya.catalogservice.repository.ProductOfferRepository;
import com.etiya.catalogservice.repository.ProductRepository;
import com.etiya.catalogservice.service.abstracts.ProductOfferService;
import com.etiya.catalogservice.service.dtos.request.productOffer.CreateProductOfferRequest;
import com.etiya.catalogservice.service.dtos.response.productOffer.CreatedProductOfferResponse;
import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.responses.ActiveProductOfferResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferServiceImpl implements ProductOfferService {

    private final ProductOfferRepository repository;
    private final ProductRepository productRepository;

    public ProductOfferServiceImpl(ProductOfferRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
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
        // 1) Ürün kontrolü
        Product product = productRepository.findById(r.getProductId())
                .orElseThrow(() -> new BusinessException("Product not found: " + r.getProductId()));

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
        po.setStartDate(r.getStartDate());
        po.setEndDate(r.getEndDate());
        po.setDiscountRate(rate);
        po.setStatus(r.getStatus());
        po.setProduct(product);

        po = repository.save(po);

        // 5) Response
        CreatedProductOfferResponse resp = new CreatedProductOfferResponse();
        resp.setId(po.getId());
        resp.setName(po.getName());
        resp.setProductId(product.getId());
        resp.setDiscountRate(po.getDiscountRate());
        return resp;
    }


    private ActiveProductOfferResponse map(ProductOffer po) {
        ActiveProductOfferResponse r = new ActiveProductOfferResponse();
        r.setProductOfferId(po.getId());
        r.setProductId(po.getProduct().getId());
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
