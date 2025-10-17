package com.etiya.customerservice.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "individual_customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class IndividualCustomer extends Customer {

    @Column(name = "firstName",nullable = false,length = 50)
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "middleName")
    private String middleName;

    @Column(name = "nationalId",unique = true,updatable = false,length = 11)
    private String nationalId;

    @Column(name = "dateOfBirth")
    private LocalDateTime dateOfBirth;

    @Column(name = "motherName")
    private String motherName;

    @Column(name = "fatherName")
    private String fatherName;

    @Column(name = "gender")
    private String gender;

}