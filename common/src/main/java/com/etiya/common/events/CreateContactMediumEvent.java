package com.etiya.common.events;

public record CreateContactMediumEvent(
        String customerId,
        int id,
        String type,
        String value,
        boolean isPrimary
) {
}
