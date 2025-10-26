package com.etiya.common.events;

public record UpdateCustomerEvent (
        String customerId,
        String customerNumber,
        String firstName,
        String lastName,
        String nationalId,
        String dateOfBirth,
        String motherName,
        String fatherName,
        String gender
){
}
