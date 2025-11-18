package com.etiya.searchservice.transport.kafka.consumer.customer;

import com.etiya.common.events.customer.DeleteCustomerEvent;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class DeletedCustomerConsumer {
    private final CustomerSearchService customerSearchService;
    private final Logger LOGGER = LoggerFactory.getLogger(DeletedCustomerConsumer.class);

    public DeletedCustomerConsumer(CustomerSearchService customerSearchService) {
        this.customerSearchService = customerSearchService;
    }

    @Bean
    public Consumer<DeleteCustomerEvent> customerDeleted() {
        return event -> {
            LOGGER.info("Received DeleteCustomerEvent: {}", event);
            customerSearchService.delete(event.customerId()); // Servis metodunu çağır
        };
    }
}
