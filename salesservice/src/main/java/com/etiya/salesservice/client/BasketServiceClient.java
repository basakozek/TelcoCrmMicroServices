package com.etiya.salesservice.client;

import com.etiya.salesservice.service.dtos.BasketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "basketservice") // basketservice'in Eureka'daki uygulama adı
public interface BasketServiceClient {

    /**
     * BasketController'daki yeni endpoint'i çağırır.
     */
    @GetMapping("/api/baskets/{billingAccountId}")
    BasketDTO getByBillingAccountId(@PathVariable int billingAccountId);
}
