package com.etiya.customerservice.service.requests.district;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateDistrictRequest {
    @NotBlank(message = "{district.name.notBlank}")
    @Length(max = 20, message = "{district.name.length}")
    private String name;

    @NotNull(message = "{district.cityId.notNull}")
    private Integer cityId;
}
