package com.etiya.catalogservice.repository;

import com.etiya.catalogservice.domain.entities.CatalogProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CatalogProductOfferRepository extends JpaRepository<CatalogProductOffer, Integer> {
    // Tek katalog (mevcut)
    @Query("""
        select cpo from CatalogProductOffer cpo
        join fetch cpo.catalog c
        join fetch cpo.productOffer po
        join fetch po.productSpecification ps
        where c.id = :catalogId
        """)
    List<CatalogProductOffer> findAllByCatalogIdWithDetail(@Param("catalogId") int catalogId);

    // Tek katalog + aktif (mevcut)
    @Query("""
        select cpo from CatalogProductOffer cpo
        join fetch cpo.catalog c
        join fetch cpo.productOffer po
        join fetch po.productSpecification ps
        where c.id = :catalogId
          and upper(po.status) = 'ACTIVE'
          and po.startDate <= :now
          and (po.endDate is null or po.endDate >= :now)
        """)
    List<CatalogProductOffer> findActiveByCatalogIdWithDetail(@Param("catalogId") int catalogId,
                                                              @Param("now") java.time.LocalDateTime now);

    // YENİ: Çoklu katalog id’si (descendants için)
    @Query("""
        select distinct cpo from CatalogProductOffer cpo
        join fetch cpo.catalog c
        join fetch cpo.productOffer po
        join fetch po.productSpecification ps
        where c.id in :catalogIds
        """)
    List<CatalogProductOffer> findAllByCatalogIdsWithDetail(@Param("catalogIds") List<Integer> catalogIds);

    // YENİ: Çoklu katalog id’si + aktif filtre
    @Query("""
        select distinct cpo from CatalogProductOffer cpo
        join fetch cpo.catalog c
        join fetch cpo.productOffer po
        join fetch po.productSpecification ps
        where c.id in :catalogIds
          and upper(po.status) = 'ACTIVE'
          and po.startDate <= :now
          and (po.endDate is null or po.endDate >= :now)
        """)
    List<CatalogProductOffer> findActiveByCatalogIdsWithDetail(@Param("catalogIds") List<Integer> catalogIds,
                                                               @Param("now") java.time.LocalDateTime now);
}
