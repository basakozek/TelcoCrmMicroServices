package com.etiya.customerservice.service.requests.address;

import com.etiya.common.localization.LocalizationService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressRequest {

    @NotBlank(message = "{address.street.notBlank}")
    private String street;

    @NotBlank(message = "{address.houseNumber.notBlank}")
    private String houseNumber;

    @Size(max = 250, message = "{address.description.size}")
    private String description;

    private boolean isDefault;
    private int districtId;
    private UUID customerId;
}
