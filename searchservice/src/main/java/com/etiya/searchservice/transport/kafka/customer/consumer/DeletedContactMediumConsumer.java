package com.etiya.searchservice.transport.kafka.customer.consumer;

import com.etiya.common.events.DeleteContactMediumEvent;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.function.Consumer;

@Configuration
public class DeletedContactMediumConsumer {
    private  final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(DeletedContactMediumConsumer.class);

    public DeletedContactMediumConsumer(CustomerSearchService service) {
        this.service = service;
    }

    @Bean
    public Consumer<DeleteContactMediumEvent> contactMediumDeleted() {
        return event -> {
            service.deleteContactMedium(event.customerId(), event.contactMediumId());
            LOGGER.info("Delete contact medium has been successfully executed");
        };
    }

//    @KafkaListener(topics = "delete-contactmedium", groupId = "search-service")
//    public void consume(DeleteContactMediumEvent event) {
//        LOGGER.info(String.format("Received DeleteContactMediumEvent => %s", event));
//        service.deleteContactMedium(event.customerId(), event.contactMediumId());
//    }
}
