package com.etiya.common.events.address;

public record CreateAddressEvent(
        String customerId,
        int id,
        String title,
        String street,
        String houseNumber,
        String description,
        boolean isDefault,
        int districtId,
        String districtName,
        int cityId,
        String cityName,
        String createdDate,
        String updatedDate,
        String deletedDate
) {
}
