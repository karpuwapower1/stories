package com.funfic.karpilovich.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    
    Set<Book> findAllByUser(long user);

}