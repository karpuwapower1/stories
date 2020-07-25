package com.funfic.karpilovich.service;

import org.springframework.data.domain.Page;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.projection.BookProjection;
import com.funfic.karpilovich.dto.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.dto.request.BookRequest;

public interface BookService {

    Page<BookWithoutContextProjection> findBooks(int page, String sort, String order);

    Page<BookWithoutContextProjection> findByUserId(Long id, int page, String sort, String order);

    Page<BookWithoutContextProjection> findByGenre(String name, int page, String sort, String order);

    Page<BookWithoutContextProjection> findByTag(String name, int page, String sort, String order);

    BookProjection findById(Long id);

    void delete(Long id);

    Book findBookById(Long id);

    void addBook(BookRequest bookRequest, User user);

    void updateBook(Long id, BookRequest bookRequet);
}