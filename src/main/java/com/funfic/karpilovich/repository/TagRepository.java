package com.funfic.karpilovich.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.domain.Tag;
import com.funfic.karpilovich.repository.projection.TagProjection;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "SELECT name AS name, count(*) AS totalQuantity "
            + " FROM tags "
            + " LEFT JOIN books_tags ON tags.id = books_tags.tags_id "
            + " GROUP BY tags_id LIMIT 100", nativeQuery = true)
    List<TagQuantity> findFits100TagQuantityByNameNative();

    public interface TagQuantity extends TagProjection {

        Long getTotalQuantity();
    }
}