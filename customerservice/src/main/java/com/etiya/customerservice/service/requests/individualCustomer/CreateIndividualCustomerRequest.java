package com.etiya.customerservice.service.requests.individualCustomer;

import com.etiya.customerservice.service.messages.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateIndividualCustomerRequest {

    @NotBlank(message = Messages.IndividualCustomerFirstNameNotBlank)
    @Size(min = 2, max = 50, message = Messages.IndividualCustomerFirstNameLength)
    private String firstName;

    private String lastName;
    private String middleName;

    @NotBlank(message = Messages.IndividualCustomerNationalIdNotBlank)
    @Size(min = 11, max = 11, message = Messages.IndividualCustomerNationalIdLength)
    @Pattern(regexp = "^[1-9]*[02468]$", message = Messages.IndividualCustomerNationalIdPattern)
    private String nationalId;

    private LocalDateTime dateOfBirth;
    private String motherName;
    private String fatherName;
    private String gender;
}