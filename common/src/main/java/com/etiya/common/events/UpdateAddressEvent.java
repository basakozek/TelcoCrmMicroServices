package com.etiya.common.events;

public record UpdateAddressEvent(
        String customerId,
        int id,
        String street,
        String houseNumber,
        String description,
        boolean isDefault,
        int districtId,
        String districtName,
        int cityId,
        String cityName
) {
}
