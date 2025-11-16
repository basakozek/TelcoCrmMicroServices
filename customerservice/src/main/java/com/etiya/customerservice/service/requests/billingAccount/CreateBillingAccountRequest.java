package com.etiya.customerservice.service.requests.billingAccount;

import com.etiya.customerservice.service.messages.Messages;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillingAccountRequest {

    @NotNull(message = Messages.BillingAccountCustomerIdNotNull)
    private UUID customerId;

    @NotNull(message = Messages.BillingAccountAddressIdNotNull)
    @Positive(message = Messages.BillingAccountAddressIdPositive)
    private Integer addressId;

    @Size(min = 6, max = 6, message = Messages.BillingAccountAccountNumberLength)
    @Pattern(regexp = "^[0-9]{6}$", message = Messages.BillingAccountAccountNumberPattern)
    private String accountNumber;

    @NotBlank(message = Messages.BillingAccountAccountNameNotBlank)
    @Length(min = 2, max = 50, message = Messages.BillingAccountAccountNameLength)
    @Pattern(regexp = "^[\\p{L}\\p{Nd} -]+$", message= Messages.BillingAccountAccountNamePattern)
    private String accountName;
}


