package com.etiya.basketservice.transport.kafka.consumer;

import com.etiya.basketservice.service.abstracts.BasketService;
import com.etiya.common.events.ClearBasketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

public class ClearBasketConsumer {
    private final BasketService basketService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClearBasketConsumer.class);

    public ClearBasketConsumer(BasketService basketService) {
        this.basketService = basketService;
    }

    @Bean
    public Consumer<ClearBasketEvent> clearBasket() {
        return event -> {
            LOGGER.info("Received ClearBasketEvent for billing account: {}", event.billingAccountId());
            try {
                // BasketService'teki mevcut sepet temizleme metodunu çağır
                basketService.clearBasket(event.billingAccountId());
                LOGGER.info("Basket cleared successfully for billing account: {}", event.billingAccountId());
            } catch (Exception e) {
                LOGGER.error("Error clearing basket: {}", e.getMessage());
            }
        };
    }
}
