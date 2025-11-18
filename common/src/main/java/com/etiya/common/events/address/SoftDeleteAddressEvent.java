package com.etiya.common.events.address;

public record SoftDeleteAddressEvent(
        String customerId,
        int addressId,
        String updatedDate,
        String deletedDate
) {

}
