package com.etiya.customerservice.transport.kafka.producer.customer;

import com.etiya.common.events.CreateContactMediumEvent;
import com.etiya.common.events.UpdateContactMediumEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UpdateContactMediumProducer {

    //private final KafkaTemplate<String, UpdateContactMediumEvent> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateContactMediumProducer.class);
    private final StreamBridge streamBridge;
    public UpdateContactMediumProducer(/*KafkaTemplate<String, UpdateContactMediumEvent> kafkaTemplate*/StreamBridge streamBridge) {
        //this.kafkaTemplate = kafkaTemplate;

        this.streamBridge = streamBridge;
    }

    public void produceContactMediumUpdated(UpdateContactMediumEvent event) {
        streamBridge.send("contactMediumUpdated-out-0", event);
        LOGGER.info(String.format("Sending update contact medium event to kafka: %s", event));

//        Message<UpdateContactMediumEvent> message = MessageBuilder.withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC, "update-contactmedium").build();
//
//        kafkaTemplate.send(message);
    }
}
