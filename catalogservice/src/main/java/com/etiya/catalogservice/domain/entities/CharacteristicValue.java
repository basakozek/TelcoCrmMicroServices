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
@Table(name = "char_values",uniqueConstraints = {
        // Bir nitelik için aynı değer birden fazla kez girilemez
        // (Örn: "Hız" için "8 Mbps" sadece bir kez tanımlanabilir)
        // Bussiness handling eklenecek.
        @UniqueConstraint(columnNames = {"char_id", "value"})})
public class CharacteristicValue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "char_id", nullable = false) // Hangi niteliğe (Characteristic) ait
    private Characteristic characteristic;

    @Column(name = "value", nullable = false)
    private String value; // Örn: "8", "24", "Kırmızı"

    // Bu değerin (Örn: "8 Mbps") hangi ürünlerde sabitlendiğini gösteren ara tablo
    @OneToMany(mappedBy = "characteristicValue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProdOfferCharValues> prodOfferCharValues;
}
