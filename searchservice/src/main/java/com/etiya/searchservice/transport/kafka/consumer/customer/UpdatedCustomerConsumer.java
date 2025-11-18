package com.etiya.searchservice.transport.kafka.consumer.customer;

import com.etiya.common.events.customer.UpdateCustomerEvent;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class UpdatedCustomerConsumer {
    private final CustomerSearchService customerSearchService;
    private final Logger LOGGER = LoggerFactory.getLogger(UpdatedCustomerConsumer.class);

    public UpdatedCustomerConsumer(CustomerSearchService customerSearchService) {
        this.customerSearchService = customerSearchService;
    }
    @Bean
    public Consumer<UpdateCustomerEvent> customerUpdated() {
        return event -> {
            LOGGER.info("Received UpdateCustomerEvent: {}", event);
            customerSearchService.updateCustomer(event);
        };
    }
}
