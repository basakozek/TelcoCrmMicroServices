package com.etiya.customerservice.service.requests.billingAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBillingAccountRequest {
    private int id;
    private String accountName;
    private String status;
    private String type;
    private Integer addressId;
    // accuntNumber immutable tutuldu.
}
