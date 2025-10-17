package com.etiya.searchservice.transport.kafka.customer.consumer;


import com.etiya.common.events.CreateAddressEvent;
import com.etiya.searchservice.domain.Address;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

//@Service
@Configuration
public class CreatedAddressConsumer {

    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(CreatedAddressConsumer.class);
    public CreatedAddressConsumer(CustomerSearchService service) {
        this.service = service;
    }

    @Bean
    public Consumer<CreateAddressEvent> addressCreated() {
        return event -> {
            Address address = new Address(event.id(), event.street(), event.houseNumber(), event.description(),
                event.isDefault(), event.districtId(), event.districtName(), event.cityId(), event.cityName()
                    , event.createdDate(),null,null);
            service.addAddress(event.customerId(), address);
            LOGGER.info(String.format("Sending address event to kafka: %s", event));

        };
    }
}
