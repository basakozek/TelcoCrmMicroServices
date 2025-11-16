package com.etiya.searchservice.repository;

import com.etiya.searchservice.domain.CustomerSearch;
import com.etiya.searchservice.service.dtos.SearchCustomerRequest;

import java.util.List;

public interface CustomerSearchDynamicRepository {

    List<CustomerSearch> dynamicSearch(SearchCustomerRequest filters, int page, int size);

}
