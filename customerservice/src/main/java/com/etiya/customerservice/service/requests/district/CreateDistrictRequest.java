package com.etiya.customerservice.service.requests.district;

import com.etiya.customerservice.service.messages.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateDistrictRequest {
    @NotBlank(message = Messages.CityNameNotBlank)
    @Length(max = 20, message = Messages.DistrictNameLength)
    private String name;

    @NotNull(message = Messages.DistrictCityIdNotNull)
    private Integer cityId;
}
