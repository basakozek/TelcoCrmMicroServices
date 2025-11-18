package com.etiya.searchservice.transport.kafka.consumer.contactmedium;

import com.etiya.common.events.contactmedium.SoftDeleteContactMediumEvent;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class SoftDeletedContactMediumConsumer {
    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(SoftDeletedContactMediumConsumer.class);


    public SoftDeletedContactMediumConsumer(CustomerSearchService service) {
        this.service = service;
    }

    @Bean
    public Consumer<SoftDeleteContactMediumEvent>  contactMediumSoftDeleted() {
        return event -> {
            service.softDeleteContactMedium(event.customerId(), event.id(), event.deletedDate());
            LOGGER.info(String.format("SoftDeletedContactMediumEvent received: %s", event));
        };
    }
}
