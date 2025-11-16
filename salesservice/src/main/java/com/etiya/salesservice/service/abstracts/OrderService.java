package com.etiya.salesservice.service.abstracts;

import com.etiya.salesservice.service.dtos.BillingAccountProductResponse;
import com.etiya.salesservice.service.dtos.CreateOrderRequest;

import java.util.List;

public interface OrderService {
    void add(CreateOrderRequest request);

    List<BillingAccountProductResponse> getProductsForBillingAccount(int billingAccountId);
}
