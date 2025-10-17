package com.etiya.common.events;

public record DeleteAddressEvent(
        String customerId,
        int addressId

) {
}
