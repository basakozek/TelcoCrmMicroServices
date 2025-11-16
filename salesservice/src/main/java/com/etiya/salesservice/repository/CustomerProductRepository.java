package com.etiya.salesservice.repository;

import com.etiya.salesservice.domain.OrderProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerProductRepository extends MongoRepository<OrderProduct, String> {
    List<OrderProduct> findByBillingAccountId(int billingAccountId);
}
