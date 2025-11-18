package com.etiya.customerservice.transport.kafka.producer.address;

import com.etiya.common.events.address.CreateAddressEvent;
import org.springframework.cloud.stream.function.StreamBridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateAddressProducer {
    //private final KafkaTemplate<String, CreateAddressEvent> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAddressProducer.class);
    private final StreamBridge streamBridge;
    public CreateAddressProducer(/*KafkaTemplate<String, CreateAddressEvent> kafkaTemplate,*/ StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
        //this.kafkaTemplate = kafkaTemplate;
    }

    public void produceAddressCreated(CreateAddressEvent event) {
        streamBridge.send("addressCreated-out-0", event);
        LOGGER.info(String.format("Sending CreateAddressEvent to KafkaProducer: %s", event));

//        Message<CreateAddressEvent> message = MessageBuilder.withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC, "create-address").build();
//
//        kafkaTemplate.send(message);
    }
}
