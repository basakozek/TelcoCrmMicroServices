package com.etiya.customerservice.repository;

import com.etiya.customerservice.domain.entities.IndividualCustomer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualCustomerRepository extends CustomerRepository<IndividualCustomer> {
    // JPQL
    @Query("SELECT ic FROM IndividualCustomer ic LEFT JOIN FETCH ic.addresses a LEFT JOIN FETCH a.district d LEFT JOIN FETCH d.city")
    List<IndividualCustomer> findAllWithAddresses();

    List<IndividualCustomer> findByLastNameIgnoreCase(String lastName);

    // JPQL
    // Belirli bir isimle başlayan müşteriler
    @Query("SELECT ic FROM IndividualCustomer ic WHERE ic.firstName LIKE :prefix%")
    List<IndividualCustomer> findByFirstNameStartingPrefix(@Param("prefix") String prefix);

    // Native Query
    // Belli bir pattern ile customerNumber'a göre filtreleme.

    @Query(value = "SELECT * FROM individual_customers ic JOIN customers c on ic.customer_id = c.id WHERE c.customer_number LIKE :pattern%", nativeQuery = true)
    List<IndividualCustomer> findByCustomerNumberPattern(@Param("pattern") String pattern);

    boolean existsByNationalId(String identityNumber);


}
