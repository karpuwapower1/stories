package com.funfic.karpilovich.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.dto.projection.BookProjection;
import com.funfic.karpilovich.dto.projection.BookWithoutContextProjection;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<BookWithoutContextProjection> findBy(Pageable pageable);

    Page<BookWithoutContextProjection> findByUserId(Pageable pageable, Long userId);

    Page<BookWithoutContextProjection> findByGenresName(Pageable pageable, String name);

    Optional<BookProjection> findByIdOrderByChaptersNumber(Long id);

    Page<BookWithoutContextProjection> findByTagsName(Pageable pageable, String name);
}