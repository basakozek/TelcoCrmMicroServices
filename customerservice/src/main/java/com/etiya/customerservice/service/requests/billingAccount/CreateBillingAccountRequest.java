package com.etiya.customerservice.service.requests.billingAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillingAccountRequest {

    @NotNull(message = "{billingAccount.customerId.notNull}")
    @Positive(message = "{billingAccount.customerId.positive}")
    private UUID customerId;

    @NotNull(message = "{billingAccount.addressId.notNull}")
    @Positive(message = "{billingAccount.addressId.positive}")
    private Integer addressId;

    @NotBlank(message = "{billingAccount.type.notBlank}")
    @Pattern(
            regexp = "^(?i)(INDIVIDUAL|CORPORATE|PREPAID|POSTPAID)$",
            message = "{billingAccount.type.pattern}"
    )
    private String type;

    @NotBlank(message = "{billingAccount.status.notBlank}")
    @Pattern(
            regexp = "^(?i)(ACTIVE|SUSPENDED|CLOSED)$",
            message = "{billingAccount.status.pattern}"
    )
    private String status;

    @NotBlank(message = "{billingAccount.accountNumber.notBlank}")
    @Length(min = 3, max = 100, message = "{billingAccount.accountNumber.length}")
    @Pattern(regexp = "^[\\p{L}\\p{Nd} -]+$", message = "{billingAccount.accountNumber.pattern}")
    private String accountNumber;

    @NotBlank(message = "{billingAccount.accountName.notBlank}")
    @Length(min = 3, max = 100, message = "{billingAccount.accountName.length}")
    @Pattern(regexp = "^[\\p{L}\\p{Nd} -]+$", message = "{billingAccount.accountName.pattern}")
    private String accountName;
}


