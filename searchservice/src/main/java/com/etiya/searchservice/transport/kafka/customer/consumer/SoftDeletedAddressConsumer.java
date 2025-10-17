package com.etiya.searchservice.transport.kafka.customer.consumer;

import com.etiya.common.events.DeleteAddressEvent;
import com.etiya.common.events.SoftDeleteAddressEvent;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class SoftDeletedAddressConsumer {

    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(SoftDeletedAddressConsumer.class);

    public SoftDeletedAddressConsumer(CustomerSearchService service) {
        this.service = service;
    }
    @Bean
    public Consumer<SoftDeleteAddressEvent> addressSoftDeleted(){
        return event -> {
            service.softDeleteAddress(event.customerId(), event.addressId(), event.updatedDate(), event.deletedDate());
            LOGGER.info(String.format("Soft Deleted address => %s",event.addressId()));
        };
    }


}
