package com.etiya.searchservice.repository;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import com.etiya.searchservice.domain.CustomerSearch;
import com.etiya.searchservice.service.dtos.SearchCustomerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class CustomerSearchRepositoryImpl implements CustomerSearchDynamicRepository{

    private final ElasticsearchOperations elasticsearchOperations;

    public CustomerSearchRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private static String wc(String s) {
        String esc = s.replaceAll("([\\\\+\\-!(){}\\[\\]^\"~:|&/])", "\\\\$1");
        return "*" + esc.trim() + "*";
    }


    @Override
    public List<CustomerSearch> dynamicSearch(SearchCustomerRequest filters, int page, int size) {

        List<Query> mustClauses = new ArrayList<>();
        List<Query> shouldClausesForGsm = new ArrayList<>();

        // --- 1) Basit alanlar ---
        if (hasText(filters.getNatId())) {
            mustClauses.add(Query.of(q -> q.wildcard(w -> w
                    .field("nationalId.keyword")
                    .value(wc(filters.getNatId()))
                    .caseInsensitive(true)
            )));
        }

        if (hasText(filters.getCustomerId())) {
            mustClauses.add(Query.of(q -> q.wildcard(w -> w
                    .field("customerNumber.keyword")
                    .value(wc(filters.getCustomerId()))
                    .caseInsensitive(true)
            )));
        }

        if (hasText(filters.getOrderNumber())) {
            mustClauses.add(Query.of(q -> q.wildcard(w -> w
                    .field("orderNumber.keyword")
                    .value(wc(filters.getOrderNumber()))
                    .caseInsensitive(true)
            )));
        }

        if (hasText(filters.getFirstName())) {
            mustClauses.add(Query.of(q -> q.wildcard(w -> w
                    .field("firstName.keyword")
                    .value(wc(filters.getFirstName()))
                    .caseInsensitive(true)
            )));
        }

        if (hasText(filters.getLastName())) {
            mustClauses.add(Query.of(q -> q.wildcard(w -> w
                    .field("lastName.keyword")
                    .value(wc(filters.getLastName()))
                    .caseInsensitive(true)
            )));
        }

        // --- 2) Nested alanlar ---

        // BillingAccounts.accountNumber
        if (hasText(filters.getAccountNumber())) {
            Query nestedAccount = Query.of(q -> q.nested(n -> n
                    .path("billingAccounts")
                    .scoreMode(ChildScoreMode.Avg)
                    .query(inner -> inner.wildcard(w -> w
                            .field("billingAccounts.accountNumber.keyword")
                            .value(wc(filters.getAccountNumber()))
                            .caseInsensitive(true)
                    ))
            ));
            mustClauses.add(nestedAccount);
        }

        // GSM
        if (hasText(filters.getGsmNumber())) {
            String digits = filters.getGsmNumber().replaceAll("\\D+", "").trim();

            // root alan
            shouldClausesForGsm.add(Query.of(q -> q.wildcard(w -> w
                    .field("gsmNumber.keyword")
                    .value(wc(digits))
                    .caseInsensitive(true)
            )));

            // nested contactMediums
            List<FieldValue> phoneTypes = List.of(
                    FieldValue.of("mobile_phone")
            );

            Query nestedContact = Query.of(q -> q.nested(n -> n
                    .path("contactMediums")
                    .scoreMode(ChildScoreMode.Avg)
                    .query(inner -> inner.bool(b -> b
                            .must(m1 -> m1.terms(t -> t
                                    .field("contactMediums.type.keyword")
                                    .terms(tv -> tv.value(phoneTypes))
                            ))
                            .must(m2 -> m2.wildcard(w -> w
                                    .field("contactMediums.value.keyword")
                                    .value(wc(digits))
                                    .caseInsensitive(true)
                            ))
                    ))
            ));
            shouldClausesForGsm.add(nestedContact);
        }

        // Deleted kayıtları dışla
        Query mustNotDeleted = Query.of(q -> q.exists(e -> e.field("deletedDate")));

        // Bool query
        Query finalQuery = Query.of(q -> q.bool(b -> {
            if (!mustClauses.isEmpty()) b.must(mustClauses);
            if (!shouldClausesForGsm.isEmpty()) {
                b.must(s -> s.bool(sb -> sb.should(shouldClausesForGsm)));
            }
            b.mustNot(mn -> mn.bool(nb -> nb.must(mustNotDeleted)));
            return b;
        }));

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(finalQuery)
                .withPageable(PageRequest.of(page, size))
                .withTrackTotalHits(true)
                .build();

        SearchHits<CustomerSearch> hits =
                elasticsearchOperations.search(searchQuery, CustomerSearch.class);

        return hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}

