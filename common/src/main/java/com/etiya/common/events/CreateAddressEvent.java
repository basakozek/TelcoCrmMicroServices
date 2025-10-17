package com.etiya.common.events;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record CreateAddressEvent(
        String customerId,
        int id,
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
