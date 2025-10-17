package com.etiya.customerservice.domain.entities;

import com.etiya.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_mediums")
public class ContactMedium extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="value", nullable = false)
    private String value;

    @Column(name="is_primary", nullable = false)
    private boolean isPrimary;


}

