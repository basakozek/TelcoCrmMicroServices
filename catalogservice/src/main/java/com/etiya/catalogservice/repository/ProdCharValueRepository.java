package com.etiya.catalogservice.repository;

import com.etiya.catalogservice.domain.entities.ProdOfferCharValues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdCharValueRepository extends JpaRepository<ProdOfferCharValues, Integer> {
}
