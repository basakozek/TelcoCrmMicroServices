package com.etiya.customerservice.service.requests.address;

import com.etiya.common.localization.LocalizationService;
import com.etiya.customerservice.service.messages.Messages;
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

    @NotBlank(message = Messages.AddressStreetNotBlank)
    private String street;

    @NotBlank(message = Messages.AddressHouseNumberNotBlank)
    private String houseNumber;

    @Size(max = 250, message = Messages.AddressDescriptionSize)
    private String description;

    private boolean isDefault;
    private int districtId;
    private UUID customerId;
}
