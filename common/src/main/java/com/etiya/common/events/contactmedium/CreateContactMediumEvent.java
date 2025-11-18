package com.etiya.common.events.contactmedium;

public record CreateContactMediumEvent(
        String customerId,
        int id,
        String type,
        String value,
        boolean isPrimary
) {
}
