package com.etiya.customerservice.repository;

import com.etiya.customerservice.domain.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findAllByOrderByNameAsc();

    @Query(value = "SELECT * FROM cities c WHERE c.name LIKE :prefix%", nativeQuery = true)
    List<City> findAllByNameStartingPrefix(String prefix);

    @Query("SELECT c From City c LEFT JOIN FETCH c.districts")
    List<City> findAllWithDistricts();

    boolean existsByNameIgnoreCase(String name);
}
