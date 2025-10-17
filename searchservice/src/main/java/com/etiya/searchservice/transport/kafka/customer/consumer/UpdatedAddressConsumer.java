package com.etiya.searchservice.transport.kafka.customer.consumer;

import com.etiya.common.events.UpdateAddressEvent;
import com.etiya.searchservice.domain.Address;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Configuration
public class UpdatedAddressConsumer {
    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(UpdatedAddressConsumer.class);

    public UpdatedAddressConsumer(CustomerSearchService service) {
        this.service = service;
    }

    @Bean
    public Consumer<UpdateAddressEvent> addressUpdated() {
        return event -> {
            Address address = new Address(event.id(), event.street(), event.houseNumber(), event.description(),
                    event.isDefault(), event.districtId(), event.districtName(), event.cityId(), event.cityName());
            service.addAddress(event.customerId(), address);
            LOGGER.info(String.format("Sending UpdateAddressEvent to kafka: %s", event));
        };

    }

//    @KafkaListener(topics = "update-address", groupId = "search-service")
//    public void consume(UpdateAddressEvent event) {
//        LOGGER.info(String.format("Received UpdateAddressEvent => %s", event));
//        service.updateAddress(event.customerId(),
//                new Address(event.id(), event.street(), event.houseNumber(), event.description(),
//                        event.isDefault(), event.districtId(), event.districtName(), event.cityId(), event.cityName()));
//    }
}
