package com.etiya.searchservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMedium {

    private int id;
    private String type;
    private String value;
    private boolean isPrimary;
}
