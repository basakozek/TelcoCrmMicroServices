package com.etiya.customerservice.service.concretes;

import com.etiya.customerservice.domain.entities.District;
import com.etiya.customerservice.repository.DistrictRepository;
import com.etiya.customerservice.service.abstracts.DistrictService;
import com.etiya.customerservice.service.mappings.DistrictMapper;
import com.etiya.customerservice.service.requests.district.CreateDistrictRequest;
import com.etiya.customerservice.service.requests.district.UpdateDistrictRequest;
import com.etiya.customerservice.service.responses.district.*;
import com.etiya.customerservice.service.rules.DistrictBusinessRules;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    public final DistrictRepository districtRepository;
    public final DistrictBusinessRules districtBusinessRules;

    public DistrictServiceImpl(DistrictRepository districtRepository, DistrictBusinessRules districtBusinessRules) {
        this.districtRepository = districtRepository;
        this.districtBusinessRules = districtBusinessRules;
    }

    @Override
    public CreatedDistrictResponse add(CreateDistrictRequest request) {
        districtBusinessRules.checkCityExistence(request.getCityId());
        District district = DistrictMapper.INSTANCE.districtFromCreateDistrictRequest(request);

        District createdDistrict = districtRepository.save(district);
        CreatedDistrictResponse response = DistrictMapper.INSTANCE.createdDistrictResponseFromDistrict(createdDistrict);
        return response;
    }

    @Override
    public UpdatedDistrictResponse update(UpdateDistrictRequest request) {
        return null;
    }

    @Override
    public List<GetListDistrictResponse> getAll() {
        List<District> districts = districtRepository.findAll();
        List<GetListDistrictResponse> response = DistrictMapper.INSTANCE.getListDistrictResponseFromDistrict(districts);

        return response;
    }

    @Override
    public GetDistrictResponse getById(int id) {
        District districts = districtRepository.findById(id).orElseThrow(() -> new RuntimeException("district not found"));
        GetDistrictResponse response = DistrictMapper.INSTANCE.getDistrictResponseFromDistrict(districts);

        return response;
    }

    @Override
    public List<GetListDistrictResponse> findDistrictByNamePrefix(String prefix) {
        List<District> districts = districtRepository.findDistrictByNamePrefix(prefix);
        List<GetListDistrictResponse> response = DistrictMapper.INSTANCE.getListDistrictResponseFromDistrict(districts);

        return response;
    }

    @Override
    public List<GetListDistrictWithAddressesResponse> findDistrictWithAddresses() {
        List<District> districts = districtRepository.findDistrictWithAddresses();
        List<GetListDistrictWithAddressesResponse> response = DistrictMapper.INSTANCE.getListDistrictWithAddressesResponseFromDistrict(districts);

        return response;
    }

    @Override
    public List<GetListDistrictResponse> findDistrictByName(String name) {
        List<District> districts = districtRepository.findDistrictByName(name);
        List<GetListDistrictResponse> response = DistrictMapper.INSTANCE.getListDistrictResponseFromDistrict(districts);

        return response;
    }

    @Override
    public void delete(int id) {
        districtBusinessRules.checkAddressExistenceBeforeDelete(id);
        District district = districtRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        districtRepository.delete(district);
    }

    @Override
    public List<GetListDistrictResponse> findByCityId(int cityId) {
        List<District> districts = districtRepository.findByCityId(cityId);
        List<GetListDistrictResponse> response = DistrictMapper.INSTANCE.getListDistrictResponseFromDistrict(districts);
        return response;
    }

}
