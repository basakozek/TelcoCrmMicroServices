package com.etiya.searchservice.transport.kafka.customer.consumer;

import com.etiya.common.events.CreateContactMediumEvent;
import com.etiya.searchservice.domain.ContactMedium;
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
public class CreateContactMediumConsumer {
    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(CreateContactMediumConsumer.class);

    public CreateContactMediumConsumer(CustomerSearchService service) {
        this.service = service;
    }

    @Bean
    public Consumer<CreateContactMediumEvent> contactMediumCreated(){
        return event -> {
            ContactMedium contactMedium = new ContactMedium(event.id(), event.type(), event.value(), event.isPrimary());
            service.addContactMedium(event.customerId(), contactMedium);
            LOGGER.info(String.format("Consumed contactmedium => %s",event.customerId()));
        };
    }


//    @KafkaListener(topics = "create-contactmedium", groupId = "search-service")
//    public void consume(CreateContactMediumEvent event) {
//        LOGGER.info(String.format("Consumed Customer => %s", event));
//        service.addContactMedium(event.customerId(),
//                new ContactMedium(event.id(), event.type(), event.value(), event.isPrimary()));
//    }
}
