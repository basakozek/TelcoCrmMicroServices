package com.etiya.catalogservice.repository;

import com.etiya.catalogservice.domain.entities.CatalogProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CatalogProductOfferRepository extends JpaRepository<CatalogProductOffer, Integer> {
    // Katalogtaki tüm offer’lar (detayla birlikte)
    @Query("""
           select cpo from CatalogProductOffer cpo
           join fetch cpo.catalog c
           join fetch cpo.productOffer po
           join fetch po.product p
           where c.id = :catalogId
           """)
    List<CatalogProductOffer> findAllByCatalogIdWithDetail(@Param("catalogId") int catalogId);

    // Sadece aktif offer’lar (status & tarih)
    @Query("""
           select cpo from CatalogProductOffer cpo
           join fetch cpo.catalog c
           join fetch cpo.productOffer po
           join fetch po.product p
           where c.id = :catalogId
             and upper(po.status) = 'ACTIVE'
             and po.startDate <= :now
             and (po.endDate is null or po.endDate >= :now)
           """)
    List<CatalogProductOffer> findActiveByCatalogIdWithDetail(@Param("catalogId") int catalogId,
                                                              @Param("now") LocalDateTime now);
}
