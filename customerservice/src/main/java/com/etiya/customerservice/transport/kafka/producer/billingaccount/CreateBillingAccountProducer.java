package com.etiya.customerservice.transport.kafka.producer.billingaccount;

import com.etiya.common.events.billingaccount.CreateBillingAccountEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class CreateBillingAccountProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateBillingAccountProducer.class);
    private final StreamBridge streamBridge;

    public CreateBillingAccountProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void produceBillingAccountCreated(CreateBillingAccountEvent event) {
        streamBridge.send("billingAccountCreated-out-0", event);
        LOGGER.info(String.format("Created Billing Account [%s]", event));
    }
}
