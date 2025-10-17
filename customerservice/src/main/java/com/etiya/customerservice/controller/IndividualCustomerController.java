package com.etiya.customerservice.controller;

import com.etiya.customerservice.service.abstracts.IndividualCustomerService;
import com.etiya.customerservice.service.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.etiya.customerservice.service.responses.individualCustomer.CreatedIndividualCustomerResponse;
import com.etiya.customerservice.service.responses.individualCustomer.GetIndividualCustomerResponse;
import com.etiya.customerservice.service.responses.individualCustomer.GetListIndividualCustomerResponse;
import com.etiya.customerservice.service.responses.individualCustomer.GetListIndividualCustomerWithAddressResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/individual-customers/")
public class IndividualCustomerController {

    private final IndividualCustomerService individualCustomerService;

    public IndividualCustomerController(IndividualCustomerService individualCustomerService) {
        this.individualCustomerService = individualCustomerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedIndividualCustomerResponse add(@Valid @RequestBody CreateIndividualCustomerRequest request){
        return individualCustomerService.add(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetListIndividualCustomerResponse> getAll() {
        return individualCustomerService.getList();
    }


    @GetMapping("findallwithaddress")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListIndividualCustomerWithAddressResponse> getListWithAddresses() {
        return individualCustomerService.findAllWithAddresses();
    }

    @GetMapping("{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetIndividualCustomerResponse> getByLastName(@PathVariable String lastName) {
        return individualCustomerService.getByLastName(lastName);
    }


    @GetMapping("getByFirstNameStartingWith/{prefix}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListIndividualCustomerResponse> getByFirstNameStartingWith(@PathVariable String prefix) {
        return individualCustomerService.getByFirstNameStartingWith(prefix);
    }

    @GetMapping("getByCustomerNumberPattern/{pattern}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListIndividualCustomerResponse> getByCustomerNumberPattern(@PathVariable String pattern) {
        return individualCustomerService.getByCustomerNumberPattern(pattern);
    }

    @GetMapping("getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetIndividualCustomerResponse getById(@PathVariable UUID id) {
        return  individualCustomerService.getById(id);
    }
}
