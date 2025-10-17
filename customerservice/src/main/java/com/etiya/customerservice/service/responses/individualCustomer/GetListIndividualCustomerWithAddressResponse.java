package com.etiya.customerservice.service.responses.individualCustomer;

import com.etiya.customerservice.service.responses.address.GetListAddressResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GetListIndividualCustomerWithAddressResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String nationalId;
    private LocalDateTime dateOfBirth;
    private String motherName;
    private String fatherName;
    private String gender;

    private List<GetListAddressResponse> addresses;
}
