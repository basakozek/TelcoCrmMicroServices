package com.etiya.salesservice.controller;

import com.etiya.salesservice.service.abstracts.OrderService;
import com.etiya.salesservice.service.dtos.BillingAccountProductResponse;
import com.etiya.salesservice.service.dtos.CreateOrderRequest;
import com.etiya.salesservice.service.dtos.OrderProductDetailResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody CreateOrderRequest request) {
        orderService.add(request);
    }

    @GetMapping("/products-by-billing-account/{billingAccountId}")
    public ResponseEntity<List<BillingAccountProductResponse>> getProductsForBillingAccount(@PathVariable int billingAccountId) {
        return ResponseEntity.ok(this.orderService.getProductsForBillingAccount(billingAccountId));
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String productId) {
        orderService.deleteProduct(productId);
    }
    @GetMapping("/products/{productId}/details")
    public ResponseEntity<OrderProductDetailResponse> getProductDetails(@PathVariable String productId) {
        return ResponseEntity.ok(this.orderService.getProductDetails(productId));
    }

}
