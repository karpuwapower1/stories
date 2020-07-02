package com.funfic.karpilovich.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funfic.karpilovich.domain.Book;

public interface BookWithoutContextRepository extends JpaRepository<Book, Long> {
    
}