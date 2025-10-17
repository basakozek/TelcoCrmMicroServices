package com.etiya.customerservice.service.requests.contactMedium;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContactMediumRequest {
    private int id;
    private String type;
    private String value;
    private Boolean isPrimary;

}
