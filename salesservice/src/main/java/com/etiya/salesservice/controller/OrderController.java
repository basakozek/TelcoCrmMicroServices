package com.etiya.salesservice.controller;

import com.etiya.salesservice.service.abstracts.OrderService;
import com.etiya.salesservice.service.dtos.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
