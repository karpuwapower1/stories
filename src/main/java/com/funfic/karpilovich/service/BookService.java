package com.funfic.karpilovich.service;

import org.springframework.data.domain.Page;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.repository.projection.BookProjection;
import com.funfic.karpilovich.repository.projection.BookWithoutContextProjection;

public interface BookService {

    Page<BookWithoutContextProjection> findBook(int page, String sort);

    Page<BookWithoutContextProjection> findByUserId(Long id, int page, String sort);

    Page<BookWithoutContextProjection> findByGenre(String name, int page, String sort);

    Page<BookWithoutContextProjection> findByTag(String name, int page, String sort);

    BookProjection findById(Long id) throws ServiceException;

    void delete(Long id) throws ServiceException;

    void addBook(BookRequest bookRequest, User user) throws ServiceException;
}