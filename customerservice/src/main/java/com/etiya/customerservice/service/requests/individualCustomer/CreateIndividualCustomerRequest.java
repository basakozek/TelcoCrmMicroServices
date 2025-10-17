package com.etiya.customerservice.service.requests.individualCustomer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateIndividualCustomerRequest {

    @NotBlank(message = "{individualCustomer.firstName.notBlank}")
    @Size(min = 2, max = 50, message = "{individualCustomer.firstName.length}")
    private String firstName;

    private String lastName;
    private String middleName;

    @NotBlank(message = "{individualCustomer.nationalId.notBlank}")
    @Size(min = 11, max = 11, message = "{individualCustomer.nationalId.length}")
    @Pattern(regexp = "^[0-9]+$", message = "{individualCustomer.nationalId.pattern}")
    private String nationalId;

    private LocalDateTime dateOfBirth;
    private String motherName;
    private String fatherName;
    private String gender;
}