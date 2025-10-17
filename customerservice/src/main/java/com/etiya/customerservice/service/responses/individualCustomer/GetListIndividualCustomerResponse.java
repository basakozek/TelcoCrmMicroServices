package com.etiya.customerservice.service.responses.individualCustomer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GetListIndividualCustomerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String nationalId;
    private LocalDateTime dateOfBirth;
    private String motherName;
    private String fatherName;
    private String gender;
}
