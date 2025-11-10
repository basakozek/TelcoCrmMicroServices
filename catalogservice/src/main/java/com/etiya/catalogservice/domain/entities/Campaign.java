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
@Table(name = "campaigns")
public class Campaign extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date") // Bitiş tarihi null olabilir
    private LocalDateTime endDate;

    @Column(name = "campaign_code", nullable = false, unique = true)
    private String campaignCode;

    @Column(name = "discount_rate", nullable = false)
    private double discountRate;

    // Bir kampanya, birden fazla ürünü kapsayabilir (CampaignProduct ara tablosu üzerinden)
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CampaignProductOffer> campaignProductOffers;
}
