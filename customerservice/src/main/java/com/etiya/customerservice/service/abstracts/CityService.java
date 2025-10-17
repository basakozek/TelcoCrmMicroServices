package com.etiya.customerservice.service.abstracts;


import com.etiya.customerservice.service.requests.city.CreateCityRequest;
import com.etiya.customerservice.service.requests.city.UpdateCityRequest;
import com.etiya.customerservice.service.responses.city.*;

import java.util.List;

public interface CityService {
    CreatedCityResponse add(CreateCityRequest request);
    UpdatedCityResponse update(UpdateCityRequest request);
    List<GetListCityResponse> getAll();
    GetCityResponse getById(int id);


    List<GetListCityResponse> findAllByOrderByNameAsc();
    List<GetListCityResponse> findAllByNameStartingPrefix(String prefix);

    List<GetCityWithDistrictsResponse> findAllWithDistricts();
}
