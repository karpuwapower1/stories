package com.funfic.karpilovich.service;

import org.springframework.data.domain.Page;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.repository.projection.BookProjection;
import com.funfic.karpilovich.repository.projection.BookWithoutContextProjection;

public interface BookService {

    Page<BookWithoutContextProjection> findBook(int page, String sort);

    Page<BookWithoutContextProjection> findByUserId(Long id, int page, String sort);

    Page<BookWithoutContextProjection> findByGenre(String name, int page, String sort);

    Page<BookWithoutContextProjection> findByTag(String name, int page, String sort);

    BookProjection findById(Long id);

    void delete(Long id);

    void addBook(BookRequest bookRequest, User user);
    
    void updateBook(Long id, BookRequest bookRequet);
}