package com.etiya.customerservice.repository;

import com.etiya.customerservice.domain.entities.ContactMedium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ContactMediumRepository extends JpaRepository<ContactMedium, Integer> {
    @Query("SELECT cm FROM ContactMedium cm WHERE cm.type = 'email'")
    List<ContactMedium> findAllByTypeMail();

    @Query(value = "SELECT * FROM contact_mediums cm Where cm.value like :prefix%", nativeQuery = true)
    List<ContactMedium> findAllByValueStartingPrefix(String prefix);

    List<ContactMedium> findAllByOrderByValueAsc();

    List<ContactMedium> findAllByCustomerIdAndIsPrimaryTrue(UUID customerId);

    @Query("select count(c)>0 from Customer c where c.id = :customerId")
    boolean existsByCustomerId(UUID customerId);



    @Query(value = """
        SELECT EXISTS(
            SELECT 1 FROM customers c
            WHERE c.id = :customerId
              AND c.deleted_date IS NULL
        )
        """, nativeQuery = true)
    boolean isCustomerActive(@Param("customerId") UUID customerId);


}
