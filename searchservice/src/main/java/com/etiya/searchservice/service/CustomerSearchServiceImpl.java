package com.etiya.searchservice.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import com.etiya.common.events.UpdateCustomerEvent;
import com.etiya.searchservice.domain.Address;
import com.etiya.searchservice.domain.BillingAccount;
import com.etiya.searchservice.domain.ContactMedium;
import com.etiya.searchservice.domain.CustomerSearch;
import com.etiya.searchservice.repository.CustomerSearchRepository;
import com.etiya.searchservice.service.dtos.SearchCustomerRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.stream;

@Service
public class CustomerSearchServiceImpl implements CustomerSearchService {

    private final CustomerSearchRepository customerSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public CustomerSearchServiceImpl(CustomerSearchRepository customerSearchRepository, ElasticsearchOperations elasticsearchOperations) {
        this.customerSearchRepository = customerSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }


    @Override
    public void add(CustomerSearch customerSearch) {
        customerSearchRepository.save(customerSearch);
    }

    @Override
    public List<CustomerSearch> findAll() {
        return StreamSupport.stream(customerSearchRepository.findAll().spliterator(), false)
                .filter(customer -> customer.getDeletedDate() == null)
                .map(this::filterDeletedBillingAccounts)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(String id) {
        customerSearchRepository.deleteById(id);

    }

    @Override
    public void updateCustomer(UpdateCustomerEvent event) {

        // Elasticsearch'ten event'teki customerId ile müşteriyi bulur
        customerSearchRepository.findById(event.customerId()).ifPresentOrElse(
                customerSearch -> {
                    // Müşteri bulunduysa, event'teki bilgilerle alanları günceller
                    customerSearch.setFirstName(event.firstName());
                    customerSearch.setLastName(event.lastName());
                    customerSearch.setMotherName(event.motherName());
                    customerSearch.setFatherName(event.fatherName());
                    customerSearch.setGender(event.gender());
                    customerSearch.setDateOfBirth(event.dateOfBirth());
                    customerSearch.setCustomerNumber(event.customerNumber());
                    customerSearch.setNationalId(event.nationalId());
                    // Güncellenmiş müşteri dökümanını Elasticsearch'e kaydeder
                    customerSearchRepository.save(customerSearch);
                    // Loglama eklenebilir:
                    // LOGGER.info("Customer {} updated in Elasticsearch based on UpdateCustomerEvent.", event.customerId());
                },
                () -> {
                    // Müşteri bulunamazsa loglama veya başka bir işlem yapılabilir

                }
        );

    }

    @Override
    public void softDelete(String id, String deletedDate) {
        var cs = customerSearchRepository.findById(id).orElseThrow();
        cs.setDeletedDate(deletedDate);
        customerSearchRepository.save(cs);

    }

    @Override
    public List<CustomerSearch> searchAllFields(String name) {
        return customerSearchRepository.searchAllFields(name)
                .stream()
                .map(this::filterDeletedBillingAccounts)
                .collect(Collectors.toList());
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
//                .stream()
//                .map(this::filterDeletedAddresses)
//                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerSearch> findByFirstNamePrefix(String prefix) {
        return customerSearchRepository.findByFirstNamePrefix(prefix);
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private static String wc(String s) {
        // wildcard için özel karakterleri kaçır, * ve ? kalsın
        String esc = s.replaceAll("([\\\\+\\-!(){}\\[\\]^\"~:|&/])", "\\\\$1");
        return "*" + esc.trim() + "*";
    }


    @Override
    public List<CustomerSearch> dynamicSearch(SearchCustomerRequest filters, int page, int size) {
        return customerSearchRepository.dynamicSearch(filters, page, size)
                .stream().map(this::filterDeletedBillingAccounts).collect(Collectors.toList());
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
    public void softDeleteAddress(String customerId, int addressId, String updatedDate, String deletedDate) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
//        cs.getAddresses().forEach(address -> {
//            if (address.getId() == addressId) {
//                address.setUpdatedDate(updatedDate);
//                address.setDeletedDate(deletedDate);
//            }
//        });
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

    @Override
    public void softDeleteContactMedium(String customerId, int id, String deletedDate) {

    }

    @Override
    public void addBillingAccount(String customerId, BillingAccount billingAccount) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getBillingAccounts().removeIf(c-> c.getId() == billingAccount.getId());
        cs.getBillingAccounts().add(billingAccount);
        customerSearchRepository.save(cs);
    }

    @Override
    public void updateBillingAccount(String customerId, BillingAccount billingAccount) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getBillingAccounts().removeIf(c-> c.getId() == billingAccount.getId());
        cs.getBillingAccounts().add(billingAccount);
        customerSearchRepository.save(cs);
    }

    @Override
    public void deleteBillingAccount(String customerId, int billingAccountId) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        cs.getBillingAccounts().removeIf(c-> c.getId() == billingAccountId);
        customerSearchRepository.save(cs);
    }

    @Override
    public void softDeleteBillingAccount(String customerId, int id, String deletedDate) {
        var cs = customerSearchRepository.findById(customerId).orElseThrow();
        if (cs.getBillingAccounts() != null) {
            cs.getBillingAccounts().forEach(ba -> {
                if (ba.getId() == id) {
                    ba.setDeletedDate(deletedDate);
                }
            });

            customerSearchRepository.save(cs);
    }}

    private CustomerSearch filterDeletedBillingAccounts(CustomerSearch cs) {
        if (cs.getBillingAccounts() != null) {
            cs.setBillingAccounts(
                    cs.getBillingAccounts().stream()
                            .filter(a -> a.getDeletedDate() == null)
                            .collect(Collectors.toList())
            );
        }
        return cs;
    }
}
