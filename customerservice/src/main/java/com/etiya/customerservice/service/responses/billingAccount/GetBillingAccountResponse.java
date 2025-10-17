package com.etiya.customerservice.service.responses.billingAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetBillingAccountResponse {
    private int id;
    private String accountName;
    private String accountNumber;
    private String status;
    private String type;
    private UUID customerId;
    private int addressId;
    private String cityName;
    private String districtName;
}
