package com.etiya.searchservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "customer-search")
public class CustomerSearch {
    @Id
    private String id;

    private String customerNumber;
    private String firstName;
    private String lastName;
    private String nationalId;
    private String dateOfBirth;
    private String motherName;
    private String fatherName;
    private String gender;

    public CustomerSearch(String id, String customerNumber, String firstName, String lastName, String nationalId, String dateOfBirth, String motherName, String fatherName, String gender) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
        this.motherName = motherName;
        this.fatherName = fatherName;
        this.gender = gender;
    }

    @Field(type = FieldType.Nested)
    private List<Address> addresses = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<ContactMedium> contactMediums = new ArrayList<>();

}
