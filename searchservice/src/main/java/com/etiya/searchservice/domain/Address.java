package com.etiya.searchservice.domain;

import com.etiya.common.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private int id;
    private String street;
    private String houseNumber;
    private String description;
    private boolean isDefault;
    private int districtId;
    private String districtName;
    private int cityId;
    private String cityName;
    private String createdDate;
    private String updatedDate;
    private String deletedDate;
}
