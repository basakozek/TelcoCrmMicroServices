package com.etiya.customerservice.service.requests.contactMedium;

import com.etiya.customerservice.service.messages.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ContactMediumValueConstraint
public class CreateContactMediumRequest {
    @NotNull(message = Messages.ContactMediumCustomerIdNotNull)
    private UUID customerId;

    @NotBlank(message = Messages.ContactMediumTypeNotBlank)
    @Pattern(
            regexp = "^(?i)(email|phone_number|social_media|address)$",
            message = Messages.ContactMediumTypePattern
    )
    private String type;

    @NotBlank(message = Messages.ContactMediumValueNotBlank)
    @Length(max = 150, message = Messages.ContactMediumValueLength)
    private String value;

    @NotNull(message = Messages.ContactMediumIsPrimaryNotNull)
    private boolean isPrimary;
}

