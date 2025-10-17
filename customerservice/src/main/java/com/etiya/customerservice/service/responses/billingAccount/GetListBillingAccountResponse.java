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


public class GetListBillingAccountResponse {

    private int id;
    private UUID customerId;
    private int addressId;
    private String districtName;
    private String cityName;
    private String type;
    private String status;
    private String accountNumber;
    private String accountName;
}
