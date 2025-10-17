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
@Table(name = "districts")
public class District extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    // Many districts belong to one city
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    // One district -> many addresses
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Address> addresses;
}

