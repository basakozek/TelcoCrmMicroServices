package com.etiya.customerservice.transport.kafka.producer.contactmedium;

import com.etiya.common.events.contactmedium.DeleteContactMediumEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class DeleteContactMediumProducer {

    //private final KafkaTemplate<String, DeleteContactMediumEvent> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteContactMediumProducer.class);
    private final StreamBridge streamBridge;
    public DeleteContactMediumProducer(/*KafkaTemplate<String, DeleteContactMediumEvent> kafkaTemplate*/StreamBridge streamBridge) {
        //this.kafkaTemplate = kafkaTemplate;
        this.streamBridge = streamBridge;
    }

    public void produceContactMediumDeleted(DeleteContactMediumEvent event) {
        streamBridge.send("contactMediumDeleted-out-0", event);
        LOGGER.info(String.format("Publishing ContactMediumDeletedEvent: %s", event));

//        Message<DeleteContactMediumEvent> message = MessageBuilder.withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC, "delete-contactmedium").build();
//
//        kafkaTemplate.send(message);
    }
}
