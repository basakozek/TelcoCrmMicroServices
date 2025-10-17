package com.etiya.customerservice.service.responses.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetListAddressResponse {
    private int  id;
    private String street;
    private String houseNumber;
    private String description;
    private boolean isDefault;
    private UUID customerId;

    private int districtId;
    private String districtName;
    private String cityName;
}
