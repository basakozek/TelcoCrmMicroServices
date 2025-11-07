package com.etiya.customerservice.service.requests.individualCustomer;

import com.etiya.customerservice.service.messages.Messages;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateIndividualCustomerRequest {

    @NotBlank(message = Messages.IndividualCustomerFirstNameNotBlank)
    @Size(min = 2, max = 50, message = Messages.IndividualCustomerFirstNameLength)
    private String firstName;

    @Size(min = 2, max = 50, message = Messages.IndividualCustomerLastNameLength)
    private String lastName;

    @Size(min = 2, max = 50, message = Messages.IndividualCustomerMiddleNameLength)
    private String middleName;

    @NotBlank(message = Messages.IndividualCustomerNationalIdNotBlank)
    @Size(min = 11, max = 11, message = Messages.IndividualCustomerNationalIdLength)
    @Pattern(regexp = "^[1-9]*[02468]$", message = Messages.IndividualCustomerNationalIdPattern)
    private String nationalId;

    @NotNull(message = Messages.DateOfBirthNotNull)
    @PastOrPresent(message = Messages.DateOfBirthCannotBeInFuture)
    private LocalDateTime dateOfBirth;

    @Size(min = 2, max = 50, message = Messages.IndividualCustomerMotherNameLength)
    private String motherName;
    @Size(min = 2, max = 50, message = Messages.IndividualCustomerFatherNameLength)
    private String fatherName;

    @NotNull(message = Messages.GenderNotNull)
    private String gender;
}