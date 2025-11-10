package com.etiya.catalogservice.domain.entities;

import com.etiya.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_specifications")
public class ProductSpecification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "lifecycle_status", nullable = false)
    private String lifecycleStatus; // Örn: "Active", "Planning"

    @Column(name = "product_type", nullable = false)
    private String productType; // Örn: "Service", "Device"

    // Bir 'Spec' (şablon), birden fazla somut 'Product' (SKU) tarafından kullanılabilir
    // Product entity'sindeki "productSpecification" alanına bağlanır
    @OneToMany(mappedBy = "productSpecification", fetch = FetchType.LAZY)
    private List<ProductOffer> productOffer;

    // Bir 'Spec' (şablon), birden fazla niteliğe (Characteristic) sahip olabilir
    // (ProductSpecCharacteristic ara tablosu üzerinden)
    @OneToMany(mappedBy = "productSpecification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductSpecCharacteristic> productSpecCharacteristics;
}
