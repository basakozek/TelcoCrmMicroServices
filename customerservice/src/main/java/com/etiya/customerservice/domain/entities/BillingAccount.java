package com.etiya.customerservice.domain.entities;

import com.etiya.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billing_accounts")
public class BillingAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name="type",nullable = false)
    private String type;

    @Column(name="status",nullable = false)
    private String status;

    @Column(name="account_number",nullable = false,unique = true)
    private String accountNumber;

    @Column(name="account_name",nullable = false)
    private String accountName;

    @PrePersist
    public void generateAccountNumber() {
        if (this.accountNumber == null || this.accountNumber.isEmpty()) {
            int year = LocalDate.now().getYear();
            int sequence = new java.util.Random().nextInt(1000000);
            this.accountNumber = String.format("BA-%d-%06d", year, sequence);
        }
    }
}

