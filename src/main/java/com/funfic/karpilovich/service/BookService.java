package com.funfic.karpilovich.service;

import org.springframework.data.domain.Page;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.projection.BookWithoutContextProjection;

public interface BookService {
    
    Page<BookWithoutContextProjection> findMostPopular();
    
    Page<Book> findLastUpdated();

}
