package com.etiya.customerservice.service.responses.district;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedDistrictResponse {
    private int id;
    private String name;
    private int cityId;
}
