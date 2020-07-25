package com.funfic.karpilovich.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.dto.projection.BookProjection;
import com.funfic.karpilovich.dto.projection.BookWithoutContextProjection;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<BookWithoutContextProjection> findBookWithoutContextProjectionBy(Pageable pageable);

    Page<BookWithoutContextProjection> findBookWithoutContextProjectionByUserId(Pageable pageable, Long userId);

    Page<BookWithoutContextProjection> findBookWithoutContextProjectionByGenresName(Pageable pageable, String name);

    Optional<BookProjection> findBookProjectionByIdOrderByChaptersNumber(Long id);

    Page<BookWithoutContextProjection> findBookWithoutContextProjectionByTagsName(Pageable pageable, String name);
}