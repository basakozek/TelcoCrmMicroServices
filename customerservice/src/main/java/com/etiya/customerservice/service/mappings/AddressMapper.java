package com.etiya.customerservice.service.mappings;

import com.etiya.customerservice.domain.entities.Address;
import com.etiya.customerservice.service.requests.address.CreateAddressRequest;
import com.etiya.customerservice.service.requests.address.UpdateAddressRequest;
import com.etiya.customerservice.service.responses.address.CreatedAddressResponse;
import com.etiya.customerservice.service.responses.address.GetAddressResponse;
import com.etiya.customerservice.service.responses.address.GetListAddressResponse;
import com.etiya.customerservice.service.responses.address.UpdatedAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);


    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "district.id", source = "districtId")
    Address addressFromCreateAddressRequest(CreateAddressRequest request);

    @Mapping(target = "districtName", source = "district.name")
    @Mapping(target = "cityName", source = "district.city.name")
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "districtId", source = "district.id")
    CreatedAddressResponse createdAddressResponseFromAddress(Address address);


    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "district", ignore = true)
    Address addressFromUpdateAddressRequest(UpdateAddressRequest request, @MappingTarget Address address);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "districtId", source = "district.id")
    UpdatedAddressResponse updatedAddressResponseFromAddress(Address address);

    List<GetListAddressResponse> getListAddressResponsesFromAddresses(List<Address> addresses);

    GetAddressResponse getAddressResponseFromAddress(Address address);

    @Mapping(target = "districtName", source = "district.name")
    @Mapping(target = "cityName", source = "district.city.name")
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "districtId", source = "district.id")
    GetListAddressResponse getListAddressResponseFromAddress(Address address);
}
