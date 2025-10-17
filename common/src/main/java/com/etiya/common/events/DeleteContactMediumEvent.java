package com.etiya.common.events;

public record DeleteContactMediumEvent(
        String customerId,
        int contactMediumId
) {
}
