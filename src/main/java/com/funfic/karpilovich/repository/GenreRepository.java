package com.funfic.karpilovich.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.domain.Genre;
import com.funfic.karpilovich.repository.projection.GenreProjection;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    @Query(value = "SELECT id AS id, name AS name, count(*) AS totalQuantity  "
            + " FROM genres "
            + " LEFT JOIN books_genres ON genres.id = books_genres.genres_id "
            + " GROUP BY genres_id", nativeQuery = true)
    List<GenreQuantity> findTagQuantityByNameNative();

    public interface GenreQuantity extends GenreProjection {

        Long getTotalQuantity();
    }
}