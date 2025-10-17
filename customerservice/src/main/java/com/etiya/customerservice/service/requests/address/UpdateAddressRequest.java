package com.etiya.customerservice.service.requests.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {
    private int id;
    private String street;
    private String houseNumber;
    private String description;
    private boolean isDefault;
    private UUID districtId;
    private int customerId;
}
