package com.etiya.common.events.contactmedium;

public record UpdateContactMediumEvent(
        String customerId,
        int id,
        String type,
        String value,
        boolean isPrimary
) {
}
