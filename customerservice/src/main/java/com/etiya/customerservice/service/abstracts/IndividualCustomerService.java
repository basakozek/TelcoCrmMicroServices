package com.etiya.customerservice.service.abstracts;

import com.etiya.customerservice.service.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.etiya.customerservice.service.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.etiya.customerservice.service.responses.individualCustomer.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IndividualCustomerService {
    //void add(IndividualCustomer individualCustomer);
    CreatedIndividualCustomerResponse add(CreateIndividualCustomerRequest request);
    List<GetListIndividualCustomerResponse> getList();

    UpdatedIndividualCustomerResponse update(UUID id, UpdateIndividualCustomerRequest request);

    List<GetListIndividualCustomerWithAddressResponse> findAllWithAddresses();
    List<GetIndividualCustomerResponse> getByLastName(String lastName);

    List<GetListIndividualCustomerResponse> getByFirstNameStartingWith(String prefix);

    List<GetListIndividualCustomerResponse> getByCustomerNumberPattern(String pattern);

    GetIndividualCustomerResponse getById(UUID id);
}
