package com.etiya.common.events;

public record UpdateContactMediumEvent(
        String customerId,
        int id,
        String type,
        String value,
        boolean isPrimary
) {
}
