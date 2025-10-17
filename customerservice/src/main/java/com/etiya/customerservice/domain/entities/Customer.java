package com.etiya.customerservice.domain.entities;

import com.etiya.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    //private int id;
    private UUID id;

    @Column(name = "customer_number")
    private String customerNumber;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ContactMedium> contactMediums;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BillingAccount> billingAccounts;

    @PrePersist
    public void generateCustomerNumber(){
        String prefix = "CUST-";
        String year = String.valueOf(java.time.Year.now().getValue());
        String randomPart = String.format("%04d",new java.util.Random().nextInt(10000));
        this.customerNumber = prefix + year + "-" + randomPart;
    }
}

