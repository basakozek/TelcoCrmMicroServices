package com.etiya.customerservice.service.responses.billingAccount;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class CreatedBillingAccountResponse {
    private int id;
    private UUID customerId;
    private int addressId;
    private String type;
    private String status;
    private String accountNumber;
    private String accountName;
}

