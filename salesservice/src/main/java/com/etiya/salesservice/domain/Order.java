package com.etiya.salesservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @MongoId
    @Field(name = "id")
    private String id;

    @Field(name = "billingAccId")
    private String billingAccId;

    @Field(name = "totalPrice")
    private double totalPrice;

    private List<OrderItem> orderItems;

    public Order(){
        this.orderItems = new ArrayList<>();
    }

    // TODO: Müşteri bilgileri => FirstName,LastName,Email
}
