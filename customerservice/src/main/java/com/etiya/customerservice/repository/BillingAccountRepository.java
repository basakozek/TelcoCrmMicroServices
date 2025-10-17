package com.etiya.customerservice.repository;

import com.etiya.customerservice.domain.entities.BillingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BillingAccountRepository extends JpaRepository<BillingAccount, Integer> {
    List<BillingAccount> findAllByOrderByAccountNameDesc();

    @Query("SELECT ba from BillingAccount ba Where ba.type = 'corporate'")
    List<BillingAccount> findAllByTypeCorporate();

    @Query(value = "SELECT * FROM billing_accounts ba Where ba.account_name like :prefix%", nativeQuery = true)
    List<BillingAccount> findAllByAccountNameStartingPrefix(String prefix);

    @Query(value = "Select count(*)>0 from addresses a where a.id = :addressId and a.customer_id = :customerId", nativeQuery = true)
    boolean checkAddressContainsCustomer(@Param("addressId") int addressId, @Param("customerId") UUID customerId);

}
