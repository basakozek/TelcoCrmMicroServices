package com.etiya.customerservice.service.abstracts;

import com.etiya.customerservice.service.requests.district.CreateDistrictRequest;
import com.etiya.customerservice.service.requests.district.UpdateDistrictRequest;
import com.etiya.customerservice.service.responses.district.*;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistrictService {
    CreatedDistrictResponse add(CreateDistrictRequest request);
    UpdatedDistrictResponse update(UpdateDistrictRequest request);
    List<GetListDistrictResponse> getAll();
    GetDistrictResponse getById(int id);


    List<GetListDistrictResponse> findDistrictByNamePrefix(String prefix);


    List<GetListDistrictWithAddressesResponse> findDistrictWithAddresses();

    List<GetListDistrictResponse> findDistrictByName(String name);

    void delete(int id);
}
