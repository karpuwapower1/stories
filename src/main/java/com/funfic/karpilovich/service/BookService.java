package com.funfic.karpilovich.service;

import org.springframework.data.domain.Page;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.BookDto;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.projection.BookWithoutContextProjection;

public interface BookService {

    Page<BookWithoutContextProjection> findMostPopular();

    Page<BookWithoutContextProjection> findLastUpdated();
    
    void delete(Long id) throws ServiceException;
    
    BookDto addBook(BookRequest bookRequest, User user) throws ServiceException;
}