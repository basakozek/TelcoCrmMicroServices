package com.etiya.salesservice.repository;

import com.etiya.salesservice.domain.OrderProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerProductRepository extends MongoRepository<OrderProduct, String> {
    List<OrderProduct> findByBillingAccountId(int billingAccountId);
    Optional<OrderProduct> findByProductOfferId(String productOfferId);
}
