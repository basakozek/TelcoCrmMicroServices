package com.etiya.customerservice.repository;

import com.etiya.customerservice.domain.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

    @Query(value = "select * from districts d where d.name like :prefix%", nativeQuery = true)
    List<District> findDistrictByNamePrefix(String prefix);

    @Query("select d from District d left join fetch d.addresses")
    List<District> findDistrictWithAddresses();

    List<District> findDistrictByName(String name);


    @Query("select (count(ba)>0) from District d join d.addresses ba where d.id = :districtId")
    boolean existsAddress(@Param("districtId") int districtId);


    @Query(value = "select (count(*) > 0) from districts d join cities c on c.id = d.city_id where c.id = :cityId", nativeQuery = true)
    boolean existCity(@Param("cityId") int cityId);
}
