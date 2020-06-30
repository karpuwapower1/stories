package com.funfic.karpilovich.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    
    List<Book> findAllByUser(long user);
}