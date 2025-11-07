package com.etiya.salesservice.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Field(name = "id")
    private String id;

    @Field(name = "productId")
    private String productId;

    @Field(name = "productName")
    private String productName;

    @Field(name = "price")
    private double price;

}
