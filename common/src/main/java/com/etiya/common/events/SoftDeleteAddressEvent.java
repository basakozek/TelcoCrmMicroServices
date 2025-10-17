package com.etiya.common.events;

public record SoftDeleteAddressEvent(
        String customerId,
        int addressId,
        String updatedDate,
        String deletedDate
) {

}
