package com.etiya.salesservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductConfiguration {
    private String key;   // Örn: "XDSL No"
    private String value; // Örn: "12345678"
}
