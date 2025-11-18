package com.etiya.searchservice.transport.kafka.consumer.customer;

import com.etiya.common.events.customer.SoftDeleteCustomerEvent;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class SoftDeletedCustomerConsumer {
    private final CustomerSearchService customerSearchService;
    private final Logger LOGGER = LoggerFactory.getLogger(SoftDeletedCustomerConsumer.class);

    public SoftDeletedCustomerConsumer(CustomerSearchService customerSearchService) {
        this.customerSearchService = customerSearchService;
    }

    @Bean
    public Consumer<SoftDeleteCustomerEvent> customerSoftDeleted() {
        return event -> {
            LOGGER.info("Received SoftDeleteCustomerEvent: {}", event);
            customerSearchService.softDelete(event.customerId(), event.deletedDate());
        };
    }
}
