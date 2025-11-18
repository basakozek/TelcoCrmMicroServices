package com.etiya.common.events.contactmedium;

public record DeleteContactMediumEvent(
        String customerId,
        int contactMediumId
) {
}
