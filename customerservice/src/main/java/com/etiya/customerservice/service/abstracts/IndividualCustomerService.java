package com.etiya.customerservice.service.abstracts;

import com.etiya.customerservice.service.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.etiya.customerservice.service.responses.individualCustomer.CreatedIndividualCustomerResponse;
import com.etiya.customerservice.service.responses.individualCustomer.GetIndividualCustomerResponse;
import com.etiya.customerservice.service.responses.individualCustomer.GetListIndividualCustomerResponse;
import com.etiya.customerservice.service.responses.individualCustomer.GetListIndividualCustomerWithAddressResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IndividualCustomerService {
    //void add(IndividualCustomer individualCustomer);
    CreatedIndividualCustomerResponse add(CreateIndividualCustomerRequest request);
    List<GetListIndividualCustomerResponse> getList();

    List<GetListIndividualCustomerWithAddressResponse> findAllWithAddresses();
    List<GetIndividualCustomerResponse> getByLastName(String lastName);

    List<GetListIndividualCustomerResponse> getByFirstNameStartingWith(String prefix);

    List<GetListIndividualCustomerResponse> getByCustomerNumberPattern(String pattern);

    GetIndividualCustomerResponse getById(UUID id);
}
