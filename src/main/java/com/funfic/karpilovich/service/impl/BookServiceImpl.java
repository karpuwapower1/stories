package com.funfic.karpilovich.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.repository.BookRepository;
import com.funfic.karpilovich.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MIN_PAGE_SIZE = 0;
    private static final String ID_COLUMN_NAME = "id";
    private static final String UPDATE_DATE_COLUMN_NAME= "updated";

    @Override
    public Page<BookWithoutContextProjection> findMostPopular() {
        return bookRepository.findBy(PageRequest.of(MIN_PAGE_SIZE, DEFAULT_PAGE_SIZE, Sort.by(ID_COLUMN_NAME).descending()));
    }

    @Override
    public Page<BookWithoutContextProjection> findLastUpdated() {
        return bookRepository.findBy(PageRequest.of(MIN_PAGE_SIZE, DEFAULT_PAGE_SIZE, Sort.by(UPDATE_DATE_COLUMN_NAME).descending()));
    }
    
    @Override
    public void delete(Long id) throws ServiceException {
        if (id == null) {
            throw new ServiceException();
        }
        bookRepository.deleteById(id);
    }
}