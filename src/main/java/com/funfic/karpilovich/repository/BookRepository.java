package com.funfic.karpilovich.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    
//    Iterable<Book> findAllByUserid(long userid);

}