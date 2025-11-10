package com.etiya.catalogservice.domain.entities;

import com.etiya.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_offers")
public class ProductOffer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date") // Bitiş tarihi null olabilir
    private LocalDateTime endDate;

    @Column(name = "discount_rate", nullable = false)
    private double discountRate;

    @Column(name = "status", nullable = false) // Örn: "Active" [cite: 36, 179]
    private String status;

    @Column(name = "stock")
    private int stock;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    private ProductSpecification productSpecification;

    // Bir teklif, birden fazla katalogda gösterilebilir
    // (CatalogProductOffer ara tablosu üzerinden)
    @OneToMany(mappedBy = "productOffer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CatalogProductOffer> catalogProductOffers;

    @OneToMany(mappedBy = "productOffer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CampaignProductOffer> campaignProductOffers;

    //(Eski Product'tan taşındı)
    @OneToMany(mappedBy = "productOffer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProdOfferCharValues> prodOfferCharValues;
}
