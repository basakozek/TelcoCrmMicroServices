package com.etiya.searchservice.controller;

import com.etiya.searchservice.domain.CustomerSearch;
import com.etiya.searchservice.service.CustomerSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-search")
public class CustomerSearchController {

    private final CustomerSearchService customerSearchService;

    public CustomerSearchController(CustomerSearchService customerSearchService) {
        this.customerSearchService = customerSearchService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSearch> findAll() {
        return customerSearchService.findAll();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        customerSearchService.delete(id);
    }

    @GetMapping("/fulltext")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSearch> search(@RequestParam String keyword) {
        return customerSearchService.searchAllFields(keyword);
    }

    @GetMapping("/by-name")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSearch> searchByFirstName(@RequestParam String firstName) {
        return customerSearchService.findByFirstName(firstName);
    }

    @GetMapping("/by-nationalId")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSearch> searchByNationalId(@RequestParam String nationalId) {
        return customerSearchService.findByNationalId(nationalId);
    }

    @GetMapping("/by-similar-name")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSearch> searchBySimilarName(@RequestParam String firstName) {
        return customerSearchService.findBySimilarFirstName(firstName);
    }

    @GetMapping("/by-date-range")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSearch> searchByDateOfBirthRange(@RequestParam String startDate,
                                                         @RequestParam String endDate) {
        return customerSearchService.findByDateOfBirthBetween(startDate, endDate);
    }

    @GetMapping("/by-city-lastname")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSearch> searchByCityAndLastName(@RequestParam String city,
                                                        @RequestParam String lastName) {
        return customerSearchService.findByCityAndLastName(city, lastName);
    }

    @GetMapping("/by-prefix")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerSearch> searchByFirstNamePrefix(@RequestParam String prefix) {
        return customerSearchService.findByFirstNamePrefix(prefix);
    }

}
