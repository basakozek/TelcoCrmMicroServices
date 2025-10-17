package com.etiya.customerservice.service.responses.address;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatedAddressResponse {
    private int id;
    private String street;
    private String houseNumber;
    private String description;
    private boolean isDefault;
    private int districtId;
    private UUID customerId;
}
