package com.etiya.searchservice.transport.kafka.consumer.billingaccount;

import com.etiya.common.events.billingaccount.CreateBillingAccountEvent;
import com.etiya.searchservice.domain.BillingAccount;
import com.etiya.searchservice.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class CreatedBillingAccountConsumer {
    private final CustomerSearchService service;
    private final Logger LOGGER = LoggerFactory.getLogger(CreatedBillingAccountConsumer.class);


    public CreatedBillingAccountConsumer(CustomerSearchService service) {
        this.service = service;
    }

    @Bean
    public Consumer<CreateBillingAccountEvent> billingAccountCreated() {
        return event -> {
            BillingAccount billingAccount = new  BillingAccount(
                    event.addressId(),
                    event.id(),
                    event.type(),
                    event.status(),
                    event.accountNumber(),
                    event.accountName()
            );
            service.addBillingAccount(event.customerId(),  billingAccount);
            LOGGER.info(String.format("Billing account created with id: %s", billingAccount.getId()));
        };

    }
}
