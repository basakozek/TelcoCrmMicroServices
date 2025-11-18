package com.etiya.customerservice.transport.kafka.producer.customer;

import com.etiya.common.events.customer.SoftDeleteCustomerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class SoftDeleteCustomerProducer {
    private final StreamBridge streamBridge;
    private static final Logger LOGGER = LoggerFactory.getLogger(SoftDeleteCustomerProducer.class);

    public SoftDeleteCustomerProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void produceCustomerSoftDeleted(SoftDeleteCustomerEvent event) {
        LOGGER.info("Sending SoftDeleteCustomerEvent: {}", event);
        streamBridge.send("customerSoftDeleted-out-0", event); // application.yml/properties'de tanımlanacak binding
    }
}
