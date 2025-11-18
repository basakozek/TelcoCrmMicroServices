package com.etiya.common.events.contactmedium;

public record SoftDeleteContactMediumEvent(
        String customerId,
        int id,
        String deletedDate
) {
}
