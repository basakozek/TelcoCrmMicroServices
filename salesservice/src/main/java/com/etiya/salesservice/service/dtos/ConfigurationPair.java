package com.etiya.salesservice.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//ADIM: Key-Value çifti
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationPair {
    private String key;
    private String value;
}
