package com.etiya.searchservice.repository;

import com.etiya.searchservice.domain.CustomerSearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSearchRepository extends ElasticsearchRepository<CustomerSearch,String> {
    @Query("""
      {
        "query_string": {
          "query": "?0",
          "analyze_wildcard": true,
          "lenient": true,
          "default_operator": "AND"
        }
      }
      """)
    List<CustomerSearch> searchAllFields(String keyword);


    @Query("""
        {
          "match": {
            "firstName": "?0"
          }
        }
    """)
    List<CustomerSearch> findByFirstName(String firstName);


    @Query("""
        {
          "term": {
            "nationalId": "?0"
          }
        }
    """)
    List<CustomerSearch> findByNationalId(String nationalId);


    @Query("""
        {
          "fuzzy": {
            "firstName": {
              "value": "?0",
              "fuzziness": 2
            }
          }
        }
    """)
    List<CustomerSearch> findBySimilarFirstName(String firstName);


    @Query("""
        {
          "range": {
            "dateOfBirth": {
              "gte": "?0",
              "lte": "?1"
            }
          }
        }
    """)
    List<CustomerSearch> findByDateOfBirthBetween(String startDate, String endDate);


    @Query("""
        {
          "bool": {
            "must": [
              { "match": { "addresses.cityName": "?0" }},
              { "term": { "lastName.keyword": "?1" }}
            ]
          }
        }
    """)
    List<CustomerSearch> findByCityAndLastName(String city, String lastName);


    @Query("""
        {
          "prefix": {
            "firstName": "?0"
          }
        }
    """)
    List<CustomerSearch> findByFirstNamePrefix(String prefix);

}
