package com.etiya.searchservice.transport.kafka.consumer.billingaccount;

import com.etiya.common.events.billingaccount.SoftDeleteBillingAccountEvent;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class SoftDeletedBillingAccountConsumer {
    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(SoftDeletedBillingAccountConsumer.class);


    public SoftDeletedBillingAccountConsumer(CustomerSearchService service) {
        this.service = service;
    }

    @Bean
    public Consumer<SoftDeleteBillingAccountEvent> billingAccountSoftDeleted(){
        return event -> {
            service.softDeleteBillingAccount(event.customerId(), event.id(), event.deletedDate());
            LOGGER.info(String.format("Soft Deleted billing account",event.id()));
        };
    }
}
