package com.etiya.salesservice.repository;

import com.etiya.salesservice.domain.OrderProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerProductRepository extends MongoRepository<OrderProduct, String> {
}
