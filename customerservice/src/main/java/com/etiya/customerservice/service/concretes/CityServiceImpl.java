package com.etiya.customerservice.service.concretes;

import com.etiya.customerservice.domain.entities.City;
import com.etiya.customerservice.repository.CityRepository;
import com.etiya.customerservice.service.abstracts.CityService;
import com.etiya.customerservice.service.mappings.CityMapper;
import com.etiya.customerservice.service.requests.city.CreateCityRequest;
import com.etiya.customerservice.service.requests.city.UpdateCityRequest;
import com.etiya.customerservice.service.responses.city.*;
import com.etiya.customerservice.service.rules.CityBusinessRules;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    public final CityRepository cityRepository;
    public final CityBusinessRules cityBusinessRules;

    public CityServiceImpl(CityRepository cityRepository, CityBusinessRules cityBusinessRules) {
        this.cityRepository = cityRepository;
        this.cityBusinessRules = cityBusinessRules;
    }

    @Override
    public CreatedCityResponse add(CreateCityRequest request) {
        cityBusinessRules.checkExistsName(request.getName());
        City city = CityMapper.INSTANCE.cityFromCreateCityRequest(request);
        City createdCity = cityRepository.save(city);

        CreatedCityResponse response = CityMapper.INSTANCE.createdCityResponseFromCity(createdCity);

        return response;
    }

    @Override
    public UpdatedCityResponse update(UpdateCityRequest request) {
        City city = cityRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("City not found"));

        City mappedCity = CityMapper.INSTANCE.cityFromUpdateCityRequest(request, city);
        City updatedCity = cityRepository.save(mappedCity);
        UpdatedCityResponse response = CityMapper.INSTANCE.updatedCityResponseFromCity(updatedCity);
        return response;
    }

    @Override
    public List<GetListCityResponse> getAll() {
        List<City> cities = cityRepository.findAll();
        List<GetListCityResponse> response = CityMapper.INSTANCE.getListCityResponseFromCity(cities);
        return response;
    }

    @Override
    public GetCityResponse getById(int id) {
        City cities = cityRepository.findById(id).orElseThrow(() -> new RuntimeException("city not found"));
        GetCityResponse response = CityMapper.INSTANCE.getCityResponseFromCity(cities);
        return response;
    }

    @Override
    public List<GetListCityResponse> findAllByOrderByNameAsc() {
        List<City> cities = cityRepository.findAllByOrderByNameAsc();
        List<GetListCityResponse> response = CityMapper.INSTANCE.getListCityResponseFromCity(cities);
        return response;
    }

    @Override
    public List<GetListCityResponse> findAllByNameStartingPrefix(String prefix) {
        List<City> cities = cityRepository.findAllByNameStartingPrefix(prefix);
        List<GetListCityResponse> response = CityMapper.INSTANCE.getListCityResponseFromCity(cities);
        return response;
    }

    @Override
    public List<GetCityWithDistrictsResponse> findAllWithDistricts() {
        List<City> cities = cityRepository.findAllWithDistricts();
        List<GetCityWithDistrictsResponse> response = CityMapper.INSTANCE.getCityWithDistrictsResponseFromCity(cities);
        return response;
    }
}
