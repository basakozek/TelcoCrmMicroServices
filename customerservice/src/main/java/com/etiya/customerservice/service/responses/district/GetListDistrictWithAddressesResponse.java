package com.etiya.customerservice.service.responses.district;

import com.etiya.customerservice.service.responses.address.GetListAddressResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetListDistrictWithAddressesResponse {
    private int id;
    private String name;
    private int cityId;
    private String cityName;
    private List<GetListAddressResponse> address;
}
