package com.etiya.customerservice.transport.kafka.producer.customer;

import com.etiya.common.events.CreateCustomerEvent;
import com.etiya.common.events.UpdateAddressEvent;
import org.hibernate.sql.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UpdateAddressProducer {
    //private final KafkaTemplate<String, UpdateAddressEvent> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAddressProducer.class);
    private final StreamBridge streamBridge;
    public UpdateAddressProducer(/*KafkaTemplate<String, UpdateAddressEvent> kafkaTemplate,*/ StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
        //this.kafkaTemplate = kafkaTemplate;
    }

    public void produceCustomerUpdated(UpdateAddressEvent event) {
        streamBridge.send("addressUpdated-out-0", event);
        LOGGER.info(String.format("Sending UpdateAddressEvent to kafka: %s", event));

//        Message<UpdateAddressEvent> message = MessageBuilder.withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC, "update-address").build();
//
//        kafkaTemplate.send(message);
    }
}
