package com.etiya.catalogservice.repository;

import com.etiya.catalogservice.domain.entities.CampaignProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignProductRepository extends JpaRepository<CampaignProductOffer, Integer> {
    // Ürüne bağlı, tarihi "aktif" kampanyalar içinde en yüksek indirimli olanı getir
    @Query("""
           select cp from CampaignProductOffer cp
           join cp.campaign c
           where cp.productOffer.id = :productOfferId
             and c.startDate <= CURRENT_TIMESTAMP
             and (c.endDate is null or c.endDate >= CURRENT_TIMESTAMP)
           """)
    List<CampaignProductOffer> findActiveByProduct(String productOfferId);

    @Query("""
           select cp from CampaignProductOffer cp
           join cp.campaign c
           where c.startDate <= CURRENT_TIMESTAMP
             and (c.endDate is null or c.endDate >= CURRENT_TIMESTAMP)
           """)
    List<CampaignProductOffer> findAllActive();

    @Query("""
       select cp from CampaignProductOffer cp
       join fetch cp.campaign c
       join fetch cp.productOffer p
       where c.id = :campaignId
       """)
    List<CampaignProductOffer> findAllByCampaignId(int campaignId);

    @Query("""
       select cp from CampaignProductOffer cp
       join fetch cp.campaign c
       join fetch cp.productOffer p
       where lower(c.name) like lower(concat('%', :campaignName, '%'))
       """)
    List<CampaignProductOffer> findAllByCampaignNameContainingIgnoreCase(String campaignName);
}
