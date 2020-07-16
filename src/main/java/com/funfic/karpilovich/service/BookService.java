package com.funfic.karpilovich.service;

import org.springframework.data.domain.Page;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.projection.BookWithoutContextProjection;

public interface BookService {

    Page<BookWithoutContextProjection> findMostPopular();

    Page<BookWithoutContextProjection> findLastUpdated();
    
    Page<BookWithoutContextProjection> findByUserId(Long id);
    
    void delete(Long id) throws ServiceException;
    
    void addBook(BookRequest bookRequest, User user) throws ServiceException;

    Page<BookWithoutContextProjection> findByGenre(String name);

    Page<BookWithoutContextProjection> findByTag(String name);
}