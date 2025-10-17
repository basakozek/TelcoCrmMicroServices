package com.etiya.customerservice.domain.entities;

import com.etiya.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Many addresses belong to one customer
    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<BillingAccount> billingAccounts;

    // Many addresses belong to one district
    @ManyToOne()
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;
}
