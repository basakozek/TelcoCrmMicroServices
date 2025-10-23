package com.etiya.customerservice.service.mappings;

import com.etiya.customerservice.domain.entities.IndividualCustomer;
import com.etiya.customerservice.service.concretes.IndividualCustomerServiceImpl;
import com.etiya.customerservice.service.mappings.AddressMapper;
import com.etiya.customerservice.service.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.etiya.customerservice.service.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.etiya.customerservice.service.responses.individualCustomer.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AddressMapper.class})
public interface IndividualCustomerMapper {

    IndividualCustomerMapper INSTANCE = Mappers.getMapper(IndividualCustomerMapper.class);

    IndividualCustomer individualCustomerFromCreateIndividualCustomerRequest(CreateIndividualCustomerRequest request);

    CreatedIndividualCustomerResponse createdIndividualCustomerResponseFromIndividualCustomer(IndividualCustomer individualCustomer);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateIndividualCustomerFromRequest(UpdateIndividualCustomerRequest request,
                                             @MappingTarget IndividualCustomer individualCustomer);

    UpdatedIndividualCustomerResponse updatedIndividualCustomerResponseFromIndividualCustomer(IndividualCustomer individualCustomer);

    GetIndividualCustomerResponse getIndividualCustomerResponseFromIndividualCustomer(IndividualCustomer individualCustomer);

    List<GetListIndividualCustomerResponse> getListIndividualCustomerResponsesFromIndividualCustomers(List<IndividualCustomer> individualCustomers);

    List<GetListIndividualCustomerWithAddressResponse> getListIndividualCustomersWithAddressResponsesFromIndividualCustomers(List<IndividualCustomer> individualCustomers);

    List<GetIndividualCustomerResponse>  getIndividualCustomerResponseFromIndividualCustomer(List<IndividualCustomer> individualCustomer);
}
