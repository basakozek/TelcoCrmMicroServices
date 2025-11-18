package com.etiya.salesservice.transport.kafka.producer;

import com.etiya.common.events.basket.ClearBasketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class ClearBasketProducer {


    private final StreamBridge streamBridge;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClearBasketProducer.class);

    public ClearBasketProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }
    /**
     * ClearBasketEvent'ini Kafka'ya fırlatır.
     * Metot adını da customerservice'teki 'produce...' convention'ına uydurdum.
     */
    public void produceClearBasketEvent(ClearBasketEvent event) {
        LOGGER.info("Publishing ClearBasketEvent for BillingAccount: {}", event.billingAccountId());

        // "clearBasket-out-0" -> application.properties'teki binding adı
        streamBridge.send("clearBasket-out-0", event);
    }
}
