package com.etiya.catalogservice.service.dtos.response.catalog;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetListCatalogResponse {
    private int id;
    private String name;
    private Integer parentId;
}
