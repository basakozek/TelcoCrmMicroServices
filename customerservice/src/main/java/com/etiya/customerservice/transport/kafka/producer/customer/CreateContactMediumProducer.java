package com.etiya.customerservice.transport.kafka.producer.customer;

import com.etiya.common.events.CreateAddressEvent;
import com.etiya.common.events.CreateContactMediumEvent;
import com.etiya.common.events.CreateCustomerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class CreateContactMediumProducer {
    //private final KafkaTemplate<String, CreateContactMediumEvent> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateContactMediumProducer.class);
    private final StreamBridge streamBridge;
    public CreateContactMediumProducer(/*KafkaTemplate<String, CreateContactMediumEvent> kafkaTemplate*/StreamBridge streamBridge) {
        //this.kafkaTemplate = kafkaTemplate;
        this.streamBridge = streamBridge ;
}

    public void produceContactMediumCreated(CreateContactMediumEvent event) {
        streamBridge.send("contactMediumCreated-out-0", event);
        LOGGER.info(String.format("Sending CreateContactMediumEvent to CreateContactMediumProducer: %s", event));

//        Message<CreateContactMediumEvent> message = MessageBuilder.withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC, "create-contactmedium").build();
//
//        kafkaTemplate.send(message);
    }
}
