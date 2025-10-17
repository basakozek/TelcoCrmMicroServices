package com.etiya.searchservice.transport.kafka.customer.consumer;

import com.etiya.common.events.UpdateContactMediumEvent;
import com.etiya.searchservice.domain.ContactMedium;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Configuration
public class UpdatedContactMediumConsumer {
    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(UpdatedContactMediumConsumer.class);

    public UpdatedContactMediumConsumer(CustomerSearchService service) {
        this.service = service;
    }

    @Bean
    public Consumer<UpdateContactMediumEvent> contactMediumUpdated() {
        return event -> {
            ContactMedium contactMedium = new ContactMedium(event.id(), event.type(), event.value(), event.isPrimary());
            service.addContactMedium(event.customerId(), contactMedium);
            LOGGER.info("Updated contact medium => " + contactMedium);
        };
    }

//    @KafkaListener(topics = "update-contactmedium", groupId = "search-service")
//    public void consume(UpdateContactMediumEvent event) {
//        LOGGER.info(String.format("Received UpdateContactMediumEvent => %s", event));
//        service.updateContactMedium(event.customerId(),
//                new ContactMedium(event.id(), event.type(), event.value(), event.isPrimary()));
//    }
}
