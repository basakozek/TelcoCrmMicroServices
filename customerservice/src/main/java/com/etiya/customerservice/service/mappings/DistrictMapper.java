package com.etiya.customerservice.service.mappings;

import com.etiya.customerservice.domain.entities.District;
import com.etiya.customerservice.service.requests.district.CreateDistrictRequest;
import com.etiya.customerservice.service.responses.district.CreatedDistrictResponse;
import com.etiya.customerservice.service.responses.district.GetDistrictResponse;
import com.etiya.customerservice.service.responses.district.GetListDistrictResponse;
import com.etiya.customerservice.service.responses.district.GetListDistrictWithAddressesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DistrictMapper {
    DistrictMapper INSTANCE = Mappers.getMapper(DistrictMapper.class);

    @Mapping(target = "city.id", source = "cityId")
    District districtFromCreateDistrictRequest(CreateDistrictRequest request);

    @Mapping(target = "cityId", source = "city.id")
    CreatedDistrictResponse createdDistrictResponseFromDistrict(District district);


    @Mapping(target = "cityId", source = "city.id")
    GetListDistrictResponse getListDistrictResponseFromDistrict(District district);

    List<GetListDistrictResponse> getListDistrictResponseFromDistrict(List<District> districts);

    @Mapping(target = "cityId", source = "city.id")
    GetDistrictResponse getDistrictResponseFromDistrict(District district);

    @Mapping(target = "address", source = "addresses")
    List<GetListDistrictWithAddressesResponse> getListDistrictWithAddressesResponseFromDistrict(List<District> district);
}
