package com.etiya.catalogservice.repository;

import com.etiya.catalogservice.domain.entities.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {

    @Query(value = """
        WITH RECURSIVE tree AS (
            SELECT id, parent_id
            FROM catalogs
            WHERE id = :rootId
          UNION ALL
            SELECT c.id, c.parent_id
            FROM catalogs c
            JOIN tree t ON c.parent_id = t.id
        )
        SELECT id FROM tree
        """, nativeQuery = true)
    List<Integer> findSubtreeIds(@Param("rootId") int rootId);
}

