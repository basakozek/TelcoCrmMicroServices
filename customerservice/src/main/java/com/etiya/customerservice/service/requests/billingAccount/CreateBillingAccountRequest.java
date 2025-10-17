package com.etiya.customerservice.service.requests.billingAccount;

import com.etiya.customerservice.service.messages.Messages;
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

    @NotNull(message = Messages.BillingAccountCustomerIdNotNull)
    @Positive(message = Messages.BillingAccountCustomerIdPositive)
    private UUID customerId;

    @NotNull(message = Messages.BillingAccountAddressIdNotNull)
    @Positive(message = Messages.BillingAccountAddressIdPositive)
    private Integer addressId;

    @NotBlank(message = Messages.BillingAccountTypeNotBlank)
    @Pattern(
            regexp = "^(?i)(INDIVIDUAL|CORPORATE|PREPAID|POSTPAID)$",
            message = Messages.BillingAccountTypePattern
    )
    private String type;

    @NotBlank(message = Messages.BillingAccountStatusNotBlank)
    @Pattern(
            regexp = "^(?i)(ACTIVE|SUSPENDED|CLOSED)$",
            message = Messages.BillingAccountStatusPattern
    )
    private String status;

    @NotBlank(message =Messages.BillingAccountAccountNumberNotBlank)
    @Length(min = 3, max = 100, message = Messages.BillingAccountAccountNumberLength)
    @Pattern(regexp = "^[\\p{L}\\p{Nd} -]+$", message = Messages.BillingAccountAccountNumberPattern)
    private String accountNumber;

    @NotBlank(message = Messages.BillingAccountAccountNameNotBlank)
    @Length(min = 3, max = 100, message = Messages.BillingAccountAccountNameLength)
    @Pattern(regexp = "^[\\p{L}\\p{Nd} -]+$", message= Messages.BillingAccountAccountNamePattern)
    private String accountName;
}


