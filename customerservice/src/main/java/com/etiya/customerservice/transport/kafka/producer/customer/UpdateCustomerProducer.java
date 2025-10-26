package com.etiya.customerservice.transport.kafka.producer.customer;

import com.etiya.common.events.UpdateCustomerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomerProducer {
    private final StreamBridge streamBridge;
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCustomerProducer.class);

    public UpdateCustomerProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
        //this.kafkaTemplate = kafkaTemplate;
    }
    public void produceCustomerUpdated(UpdateCustomerEvent event) {
        LOGGER.info("Sending UpdateCustomerEvent: {}", event);
        // "customerUpdated-out-0" -> properties dosyasında tanımlanacak binding adı
        streamBridge.send("customerUpdated-out-0", event);

    }
}
