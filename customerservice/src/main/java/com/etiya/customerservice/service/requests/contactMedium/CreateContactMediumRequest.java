package com.etiya.customerservice.service.requests.contactMedium;

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
    @NotNull(message = "{contactMedium.customerId.notNull}")
    private UUID customerId;

    @NotBlank(message = "{contactMedium.type.notBlank}")
    @Pattern(
            regexp = "^(?i)(email|phone_number|social_media|address)$",
            message = "{contactMedium.type.pattern}"
    )
    private String type;

    @NotBlank(message = "{contactMedium.value.notBlank}")
    @Length(max = 150, message = "{contactMedium.value.length}")
    private String value;

    @NotNull(message = "{contactMedium.isPrimary.notNull}")
    private boolean isPrimary;
}

