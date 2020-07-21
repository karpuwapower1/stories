package com.funfic.karpilovich.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.dto.mapper.BookMapper;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.repository.BookRepository;
import com.funfic.karpilovich.repository.projection.BookProjection;
import com.funfic.karpilovich.repository.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.service.BookService;
import com.funfic.karpilovich.service.util.SortingType;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public BookProjection findById(Long id) throws ServiceException {
        return bookRepository.findByIdOrderByChaptersNumber(id).orElseThrow(() -> new ServiceException());
    }

    @Override
    public Page<BookWithoutContextProjection> findBook(int page, String sort) {
        Sort sortingColumn = getSortingColumn(sort);
        return bookRepository.findBy(PageRequest.of(page, DEFAULT_PAGE_SIZE, sortingColumn));
    }

    @Override
    public Page<BookWithoutContextProjection> findByUserId(Long id, int page, String sort) {
        Sort sortingColumn = getSortingColumn(sort);
        return bookRepository.findByUserId(PageRequest.of(page, DEFAULT_PAGE_SIZE, sortingColumn), id);
    }

    @Override
    public Page<BookWithoutContextProjection> findByGenre(String name, int page, String sort) {
        Sort sortingColumn = getSortingColumn(sort);
        return bookRepository.findByGenresName(PageRequest.of(page, DEFAULT_PAGE_SIZE, sortingColumn), name);
    }

    @Override
    public Page<BookWithoutContextProjection> findByTag(String name, int page, String sort) {
        Sort sortingColumn = getSortingColumn(sort);
        return bookRepository.findByTagsName(PageRequest.of(page, DEFAULT_PAGE_SIZE, sortingColumn), name);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        if (id == null) {
            throw new ServiceException();
        }
        bookRepository.deleteById(id);
    }

    @Override
    public void addBook(BookRequest bookRequest, User user) throws ServiceException {
        try {
            Book book = takeBookFromRequest(bookRequest, user);
            bookRepository.saveAndFlush(book);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    private Sort getSortingColumn(String sort) {
        try {
            return mapStringToSort(sort);
        } catch (IllegalArgumentException e) {
            return Sort.unsorted();
        }
    }

    private Sort mapStringToSort(String sort) {
        SortingType type = SortingType.valueOf(sort.toUpperCase());
        return mapSortingTypeToSort(type);
    }
    
    private Sort mapSortingTypeToSort(SortingType type) {
        if (type == SortingType.NONE) {
            return Sort.unsorted();
        }
        return Sort.by(type.getSortingColumn());
    }

    private Book takeBookFromRequest(BookRequest bookRequest, User user)
            throws JsonMappingException, JsonProcessingException {
        Book book = bookMapper.mapFromBookRequestToBook(bookRequest);
        return prepareBook(book, user);
    }

    private Book prepareBook(Book book, User user) {
        book.setCreationDate(Calendar.getInstance());
        book.setUpdateDate(Calendar.getInstance());
        book.setUser(user);
        return book;
    }
}