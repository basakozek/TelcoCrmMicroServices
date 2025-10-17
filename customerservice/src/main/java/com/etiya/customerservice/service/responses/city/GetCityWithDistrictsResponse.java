package com.etiya.customerservice.service.responses.city;

import com.etiya.customerservice.service.responses.district.GetDistrictResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCityWithDistrictsResponse {
    private int id;
    private String name;
    private List<GetDistrictResponse> districts;
}
