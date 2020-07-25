package com.funfic.karpilovich.service.impl;

import java.util.Calendar;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.Tag;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.projection.BookProjection;
import com.funfic.karpilovich.dto.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.dto.request.BookRequest;
import com.funfic.karpilovich.exception.BadRequestException;
import com.funfic.karpilovich.exception.ResourceNotFoundException;
import com.funfic.karpilovich.repository.BookRepository;
import com.funfic.karpilovich.service.BookService;
import com.funfic.karpilovich.service.TagService;
import com.funfic.karpilovich.service.util.BookMapper;
import com.funfic.karpilovich.service.util.SortingType;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private TagService tagService;

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String DESCENDING_ORDER = "desc";

    @Override
    public BookProjection findById(Long id) {
        return bookRepository.findByIdOrderByChaptersNumber(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public Page<BookWithoutContextProjection> findBooks(int page, String sortingColumn, String order) {
        Sort sort = getSort(sortingColumn, order);
        return bookRepository.findBy(PageRequest.of(page, DEFAULT_PAGE_SIZE, sort));
    }

    @Override
    public Page<BookWithoutContextProjection> findByUserId(Long id, int page, String sortingColumn, String order) {
        Sort sort = getSort(sortingColumn, order);
        return bookRepository.findByUserId(PageRequest.of(page, DEFAULT_PAGE_SIZE, sort), id);
    }

    @Override
    public Page<BookWithoutContextProjection> findByGenre(String name, int page, String sortingColumn, String order) {
        Sort sort = getSort(sortingColumn, order);
        return bookRepository.findByGenresName(PageRequest.of(page, DEFAULT_PAGE_SIZE, sort), name);
    }

    @Override
    public Page<BookWithoutContextProjection> findByTag(String name, int page, String sortingColumn, String order) {
        Sort sort = getSort(sortingColumn, order);
        return bookRepository.findByTagsName(PageRequest.of(page, DEFAULT_PAGE_SIZE, sort), name);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void addBook(BookRequest bookRequest, User user) {
        try {
            addNewBook(bookRequest, user);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void updateBook(Long id, BookRequest bookRequest) {
        try {
            update(id, bookRequest);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(e);
        }
    }

    private Sort getSort(String sortingParam, String orderParam) {
        SortingType sortingType = takeSortingTypeFromString(sortingParam);
        Direction direction = getDirection(orderParam);
        return mapToSort(sortingType, direction);
    }

    private SortingType takeSortingTypeFromString(String type) {
        try {
            return SortingType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SortingType.NONE;
        }
    }

    private Sort mapToSort(SortingType type, Direction direction) {
        return type == SortingType.NONE ? Sort.unsorted() : Sort.by(direction, type.getSortingColumn());
    }

    private Direction getDirection(String order) {
        return DESCENDING_ORDER.equalsIgnoreCase(order) ? Direction.DESC : Direction.ASC;
    }

    private void addNewBook(BookRequest bookRequest, User user) throws JsonMappingException, JsonProcessingException {
        Book book = takeBookFromRequest(bookRequest, user);
        bookRepository.saveAndFlush(book);
    }

    private Book takeBookFromRequest(BookRequest bookRequest, User user)
            throws JsonMappingException, JsonProcessingException {
        Book book = bookMapper.mapFromBookRequestToBook(bookRequest);
        return prepareBook(book, user);
    }

    private Book prepareBook(Book book, User user) {
        prepareTags(book);
        book.setCreated(Calendar.getInstance());
        book.setUpdated(Calendar.getInstance());
        book.setUser(user);
        return book;
    }

    private void prepareTags(Book book) {
        Set<Tag> tags = tagService.takeCorrespondTagsFromStorage(book.getTags());
        book.setTags(tags);
    }

    private void update(Long id, BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        Book book = bookMapper.mapFromBookRequestToBook(bookRequest);
        Book bookFromDb = bookRepository.getOne(id);
        book.setCreated(bookFromDb.getCreated());
        book.setUpdated(Calendar.getInstance());
        book.setUser(bookFromDb.getUser());
        prepareTags(book);
        bookRepository.saveAndFlush(book);
    }
}