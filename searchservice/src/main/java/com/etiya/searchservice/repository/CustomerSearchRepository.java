package com.etiya.searchservice.repository;

import com.etiya.searchservice.domain.CustomerSearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSearchRepository extends ElasticsearchRepository<CustomerSearch,String>, CustomerSearchDynamicRepository {
    @Query("""
      {
        "bool": {
          "must": [
            {
              "query_string": {
                "query": "?0",
                "analyze_wildcard": true,
                "lenient": true,
                "default_operator": "AND"
              }
            }
          ],
          "must_not": [
            {
              "exists": {
                "field": "deletedDate"
              }
            }
          ]
        }
      }
      """)
    List<CustomerSearch> searchAllFields(String keyword);


    @Query("""
        {
          "bool": {
            "must": [
              {
                "match": {
                  "firstName": "?0"
                }
              }
            ],
            "must_not": [
              {
                "exists": {
                  "field": "deletedDate"
                }
              }
            ]
          }
        }
    """)
    List<CustomerSearch> findByFirstName(String firstName);


    @Query("""
        {
          "bool": {
            "must": [
              {
                "term": {
                  "nationalId": "?0"
                }
              }
            ],
            "must_not": [
              {
                "exists": {
                  "field": "deletedDate"
                }
              }
            ]
          }
        }
    """)
    List<CustomerSearch> findByNationalId(String nationalId);


    @Query("""
        {
          "bool": {
            "must": [
              {
                "fuzzy": {
                  "firstName": {
                    "value": "?0",
                    "fuzziness": 2
                  }
                }
              }
            ],
            "must_not": [
              {
                "exists": {
                  "field": "deletedDate"
                }
              }
            ]
          }
        }
    """)
    List<CustomerSearch> findBySimilarFirstName(String firstName);


    @Query("""
        {
          "bool": {
            "must": [
              {
                "range": {
                  "dateOfBirth": {
                    "gte": "?0",
                    "lte": "?1"
                  }
                }
              }
            ],
            "must_not": [
              {
                "exists": {
                  "field": "deletedDate"
                }
              }
            ]
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
            ],
            "must_not": [
              {
                "exists": {
                  "field": "deletedDate"
                }
              }
            ]
          }
        }
    """)
    List<CustomerSearch> findByCityAndLastName(String city, String lastName);


    @Query("""
        {
          "bool": {
            "must": [
              {
                "prefix": {
                  "firstName": "?0"
                }
              }
            ],
            "must_not": [
              {
                "exists": {
                  "field": "deletedDate"
                }
              }
            ]
          }
        }
    """)
    List<CustomerSearch> findByFirstNamePrefix(String prefix);

}
