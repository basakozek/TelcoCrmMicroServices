package com.etiya.customerservice.service.responses.contactMedium;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetContactMediumResponse {
    private int id;
    private UUID customerId;
    private String type;
    private String value;
    private boolean isPrimary;

}
