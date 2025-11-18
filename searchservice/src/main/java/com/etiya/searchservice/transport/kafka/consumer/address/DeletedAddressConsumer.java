package com.etiya.searchservice.transport.kafka.consumer.address;

import com.etiya.common.events.address.DeleteAddressEvent;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
//@Service
@Configuration
public class DeletedAddressConsumer {

    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(DeletedAddressConsumer.class);

    public DeletedAddressConsumer(CustomerSearchService service) {
        this.service = service;
    }
    @Bean
    public Consumer<DeleteAddressEvent> addressDeleted(){
        return event -> {
            service.deleteAddress(event.customerId(), event.addressId());
            LOGGER.info(String.format("Deleted address => %s",event.addressId()));
        };
    }

//    @KafkaListener(topics = "delete-address", groupId = "search-service")
//    public void consume(DeleteAddressEvent event) {
//        LOGGER.info(String.format("Received DeleteAddressEvent => %s", event));
//        service.deleteAddress(event.customerId(), event.addressId());
//    }
}
