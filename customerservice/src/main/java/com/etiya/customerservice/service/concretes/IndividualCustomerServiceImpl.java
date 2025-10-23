package com.etiya.customerservice.service.concretes;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.events.CreateCustomerEvent;
import com.etiya.customerservice.domain.entities.IndividualCustomer;
import com.etiya.customerservice.repository.IndividualCustomerRepository;
import com.etiya.customerservice.service.abstracts.IndividualCustomerService;
import com.etiya.customerservice.service.mappings.IndividualCustomerMapper;
import com.etiya.customerservice.service.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.etiya.customerservice.service.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.etiya.customerservice.service.responses.individualCustomer.*;
import com.etiya.customerservice.service.rules.IndividualCustomerBusinessRules;
import com.etiya.customerservice.transport.kafka.producer.customer.CreateCustomerProducer;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class IndividualCustomerServiceImpl implements IndividualCustomerService {

    private final IndividualCustomerRepository individualCustomerRepository;
    private final IndividualCustomerBusinessRules individualCustomerBusinessRules;
    private final CreateCustomerProducer createCustomerProducer;

    public IndividualCustomerServiceImpl(IndividualCustomerRepository individualCustomerRepository, IndividualCustomerBusinessRules individualCustomerBusinessRules, CreateCustomerProducer createCustomerProducer) {
        this.individualCustomerRepository = individualCustomerRepository;
        this.individualCustomerBusinessRules =  individualCustomerBusinessRules;
        this.createCustomerProducer = createCustomerProducer;
    }

    @Override
    public CreatedIndividualCustomerResponse add(CreateIndividualCustomerRequest request) {
        individualCustomerBusinessRules.checkIfIndividualCustomerExistsByIdentityNumber(request.getNationalId());

        IndividualCustomer individualCustomer = IndividualCustomerMapper.INSTANCE.individualCustomerFromCreateIndividualCustomerRequest(request);
        IndividualCustomer createdIndividualCustomer = individualCustomerRepository.save(individualCustomer);

        CreateCustomerEvent event =
                new CreateCustomerEvent(createdIndividualCustomer.getId().toString(),
                        createdIndividualCustomer.getCustomerNumber(),
                        createdIndividualCustomer.getFirstName(),
                        createdIndividualCustomer.getLastName(),
                        createdIndividualCustomer.getNationalId(),
                        createdIndividualCustomer.getDateOfBirth().toString(),
                        createdIndividualCustomer.getMotherName(),
                        createdIndividualCustomer.getFatherName(),
                        createdIndividualCustomer.getGender());

        createCustomerProducer.produceCustomerCreated(event);

        CreatedIndividualCustomerResponse response =
                IndividualCustomerMapper.INSTANCE.createdIndividualCustomerResponseFromIndividualCustomer(createdIndividualCustomer);

        return response;
    }



    @Override
    public List<GetListIndividualCustomerResponse> getList() {
        List<IndividualCustomer> individualCustomers = individualCustomerRepository.findAll();

        List<GetListIndividualCustomerResponse> responses = IndividualCustomerMapper.INSTANCE.getListIndividualCustomerResponsesFromIndividualCustomers(individualCustomers);

        return responses;
    }

    @Override
    public UpdatedIndividualCustomerResponse update(UUID id, UpdateIndividualCustomerRequest request) {
        IndividualCustomer existingCustomer = individualCustomerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Customer not found with id: " + id));

        // Sadece request’te DOLU gelen alanlar existingCustomer’a yazılır;
        // null olanlar olduğu gibi kalır.
        IndividualCustomerMapper.INSTANCE.updateIndividualCustomerFromRequest(request, existingCustomer);

        // İstersen değişmemesi gereken alanları burada kilitle:
        // existingCustomer.setNationalId(existingCustomer.getNationalId()); // örnek

        IndividualCustomer saved = individualCustomerRepository.save(existingCustomer);
        return IndividualCustomerMapper.INSTANCE.updatedIndividualCustomerResponseFromIndividualCustomer(saved);
    }


    @Override
    public List<GetListIndividualCustomerWithAddressResponse> findAllWithAddresses() {
        List<IndividualCustomer> individualCustomers = individualCustomerRepository.findAllWithAddresses();

        List<GetListIndividualCustomerWithAddressResponse> responses = IndividualCustomerMapper.INSTANCE.getListIndividualCustomersWithAddressResponsesFromIndividualCustomers(individualCustomers);

        return responses;
    }

    @Override
    public List<GetIndividualCustomerResponse> getByLastName(String lastName) {
        List<IndividualCustomer> individualCustomer = individualCustomerRepository.findByLastNameIgnoreCase(lastName);
        List<GetIndividualCustomerResponse> response =
                IndividualCustomerMapper.INSTANCE.getIndividualCustomerResponseFromIndividualCustomer(individualCustomer);
        return response;
    }

    @Override
    public List<GetListIndividualCustomerResponse> getByFirstNameStartingWith(String prefix) {
        List<IndividualCustomer> individualCustomers = individualCustomerRepository.findByFirstNameStartingPrefix(prefix);

        List<GetListIndividualCustomerResponse> response = IndividualCustomerMapper.INSTANCE.getListIndividualCustomerResponsesFromIndividualCustomers(individualCustomers);
        return response;
    }

    @Override
    public List<GetListIndividualCustomerResponse> getByCustomerNumberPattern(String pattern) {
        List<IndividualCustomer> individualCustomers = individualCustomerRepository.findByCustomerNumberPattern(pattern);

        List<GetListIndividualCustomerResponse> response = IndividualCustomerMapper.INSTANCE.getListIndividualCustomerResponsesFromIndividualCustomers(individualCustomers);
        return response;
    }

    @Override
    public GetIndividualCustomerResponse getById(UUID id) {
        individualCustomerBusinessRules.checkIfCustomerId(id);
        IndividualCustomer individualCustomer = individualCustomerRepository.findById(id).get();
        GetIndividualCustomerResponse response = IndividualCustomerMapper.INSTANCE.getIndividualCustomerResponseFromIndividualCustomer(individualCustomer);
        return response;
    }


}
