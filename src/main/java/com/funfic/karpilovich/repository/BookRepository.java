package com.funfic.karpilovich.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.projection.BookWithoutContextProjection;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Page<Book> findAllByUser(long user, Pageable pageable);
    
    Page<BookWithoutContextProjection> findBy(Pageable pageable);
    
    Optional<Book> findByIdOrderByChaptersNumberAsc(Long id);
}