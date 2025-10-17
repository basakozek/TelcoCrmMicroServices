package com.etiya.customerservice.transport.kafka.producer.customer;

import com.etiya.common.events.DeleteAddressEvent;
import com.etiya.common.events.SoftDeleteAddressEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class SoftDeleteAddressProducer {

    //private final KafkaTemplate<String, DeleteAddressEvent> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(SoftDeleteAddressProducer.class);
    private final StreamBridge streamBridge;
    public SoftDeleteAddressProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
        //this.kafkaTemplate = kafkaTemplate;
    }

    public void produceAddressSoftDeleted(SoftDeleteAddressEvent event) {
        streamBridge.send("addressSoftDeleted-out-0", event);
        LOGGER.info(String.format("Sending SoftDeleteAddressEvent: %s", event));

//        Message<DeleteAddressEvent> message = MessageBuilder.withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC, "delete-address").build();
//
//        kafkaTemplate.send(message);
    }
}
