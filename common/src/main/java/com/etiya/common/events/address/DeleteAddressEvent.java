package com.etiya.common.events.address;

public record DeleteAddressEvent(
        String customerId,
        int addressId

) {
}
