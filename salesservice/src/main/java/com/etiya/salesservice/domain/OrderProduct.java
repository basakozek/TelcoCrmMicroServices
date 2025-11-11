package com.etiya.salesservice.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "salesProducts") // Yeni koleksiyon
public class OrderProduct {

    @MongoId
    @Field(name = "id")
    private String id;

    @Field(name = "billingAccountId")
    private int billingAccountId; // Bağlı olduğu fatura hesabı

    @Field(name = "productOfferId")
    private String productOfferId; // Catalog'daki ProductOffer'ın ID'si

    @Field(name = "productOfferName")
    private String productOfferName;

    @Field(name = "status")
    private String status; // Örn: "Active", "Pending_Activation", "Deactive"

    // Dinamik konfigürasyon verileri (XDSL No, Modem SN vb.)
    @Field(name = "configuration")
    private List<ProductConfiguration> configuration;
}

