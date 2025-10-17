package com.etiya.customerservice.service.responses.contactMedium;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedContactMediumResponse {
    private int id;
    private UUID customerId;
    private String type;
    private String value;
    private Boolean isPrimary;
}
