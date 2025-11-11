package com.etiya.salesservice.service.abstracts;

import com.etiya.salesservice.service.dtos.CreateOrderRequest;

public interface OrderService {
    void add(CreateOrderRequest request);
}
