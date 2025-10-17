package com.etiya.searchservice.service;

import com.etiya.searchservice.domain.Address;
import com.etiya.searchservice.domain.ContactMedium;
import com.etiya.searchservice.domain.CustomerSearch;
import com.etiya.searchservice.repository.CustomerSearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerSearchServiceImpl implements CustomerSearchService {

    private final CustomerSearchRepository customerSearchRepository;

    public CustomerSearchServiceImpl(CustomerSearchRepository customerSearchRepository) {
        this.customerSearchRepository = customerSearchRepository;
    }


    @Override
    public void add(CustomerSearch customerSearch) {
        customerSearchRepository.save(customerSearch);
    }

    @Override
    public List<CustomerSearch> findAll() {
        return StreamSupport.stream(customerSearchRepository.findAll().spliterator(),false).collect(Collectors.toList());

    }

    @Override
    public void delete(String id) {

        customerSearchRepository.deleteById(id);

    }

    @Override
    public List<CustomerSearch> searchAllFields(String name) {
        return customerSearchRepository.searchAllFields(name);
    }

    @Override
    public List<CustomerSearch> findByFirstName(String firstName) {
        return customerSearchRepository.findByFirstName(firstName);
    }

    @Override
    public List<CustomerSearch> findByNationalId(String nationalId) {
        return customerSearchRepository.findByNationalId(nationalId);
    }

    @Override
    public List<CustomerSearch> findBySimilarFirstName(String firstName) {
        return customerSearchRepository.findBySimilarFirstName(firstName);
    }

    @Override
    public List<CustomerSearch> findByDateOfBirthBetween(String startDate, String endDate) {
        return customerSearchRepository.findByDateOfBirthBetween(startDate, endDate);
    }

    @Override
    public List<CustomerSearch> findByCityAndLastName(String city, String lastName) {
        return customerSearchRepository.findByCityAndLastName(city, lastName);
    }

    @Override
    public List<CustomerSearch> findByFirstNamePrefix(String prefix) {
        return customerSearchRepository.findByFirstNamePrefix(prefix);
    }

    @Override
    public void addAddress(String customerId, Address address) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getAddresses().removeIf(a -> a.getId() == address.getId()); // idempotent
        cs.getAddresses().add(address);
        customerSearchRepository.save(cs);
    }

    @Override
    public void updateAddress(String customerId, Address address) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getAddresses().removeIf(a -> a.getId() == address.getId());
        cs.getAddresses().add(address);
        customerSearchRepository.save(cs);
    }

    @Override
    public void deleteAddress(String customerId, int addressId) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getAddresses().removeIf(a -> a.getId() == addressId);
        customerSearchRepository.save(cs);
    }

    @Override
    public void addContactMedium(String customerId, ContactMedium contact) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getContactMediums().removeIf(c -> c.getId() == contact.getId());
        cs.getContactMediums().add(contact);
        customerSearchRepository.save(cs);
    }

    @Override
    public void updateContactMedium(String customerId, ContactMedium contact) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getContactMediums().removeIf(c -> c.getId() == contact.getId());
        cs.getContactMediums().add(contact);
        customerSearchRepository.save(cs);
    }

    @Override
    public void deleteContactMedium(String customerId, int contactId) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getContactMediums().removeIf(c -> c.getId() == contactId);
        customerSearchRepository.save(cs);
    }
}
