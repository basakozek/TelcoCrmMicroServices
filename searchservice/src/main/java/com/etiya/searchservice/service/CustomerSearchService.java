package com.etiya.searchservice.service;

import com.etiya.common.events.customer.UpdateCustomerEvent;
import com.etiya.searchservice.domain.Address;
import com.etiya.searchservice.domain.BillingAccount;
import com.etiya.searchservice.domain.ContactMedium;
import com.etiya.searchservice.domain.CustomerSearch;
import com.etiya.searchservice.service.dtos.SearchCustomerRequest;

import java.util.List;

public interface CustomerSearchService {
    void add(CustomerSearch customerSearch);
    List<CustomerSearch> findAll();
    void delete(String id);
    void updateCustomer(UpdateCustomerEvent event);
    void softDelete(String id, String deletedDate);

    List<CustomerSearch> searchAllFields(String name);
    List<CustomerSearch> findByFirstName(String firstName);
    List<CustomerSearch> findByNationalId(String nationalId);
    List<CustomerSearch> findBySimilarFirstName(String firstName);
    List<CustomerSearch> findByDateOfBirthBetween(String startDate, String endDate);
    List<CustomerSearch> findByCityAndLastName(String city, String lastName);
    List<CustomerSearch> findByFirstNamePrefix(String prefix);
    List<CustomerSearch> dynamicSearch(SearchCustomerRequest filters, int page, int size);

    // Address ops
    void addAddress(String customerId, Address address);
    void updateAddress(String customerId, Address address);
    void deleteAddress(String customerId, int addressId);
    void softDeleteAddress(String customerId, int addressId,String updatedDate,String deletedDate);

    // ContactMedium ops
    void addContactMedium(String customerId, ContactMedium contact);
    void updateContactMedium(String customerId, ContactMedium contact);
    void deleteContactMedium(String customerId, int contactId);
    void softDeleteContactMedium(String customerId, int id, String deletedDate);

    // BillingAccount ops
    void addBillingAccount(String customerId, BillingAccount billingAccount);
    void updateBillingAccount(String customerId, BillingAccount billingAccount);
    void deleteBillingAccount(String customerId, int billingAccountId);
    void softDeleteBillingAccount(String customerId, int id, String deletedDate);
}
