package com.etiya.customerservice.service.mappings;

import com.etiya.customerservice.domain.entities.City;
import com.etiya.customerservice.service.requests.city.CreateCityRequest;
import com.etiya.customerservice.service.requests.city.UpdateCityRequest;
import com.etiya.customerservice.service.responses.city.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    City cityFromCreateCityRequest(CreateCityRequest request);

    CreatedCityResponse createdCityResponseFromCity(City city);

    List<GetListCityResponse> getListCityResponseFromCity(List<City> cities);

    GetCityResponse getCityResponseFromCity(City city);

    City cityFromUpdateCityRequest(UpdateCityRequest request, @MappingTarget City city);

    UpdatedCityResponse updatedCityResponseFromCity(City city);

    List<GetCityWithDistrictsResponse> getCityWithDistrictsResponseFromCity(List<City> cities);
}
