package com.funfic.karpilovich.service.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.Chapter;
import com.funfic.karpilovich.domain.Genre;
import com.funfic.karpilovich.domain.Tag;
import com.funfic.karpilovich.dto.request.BookRequest;

@Component
public class BookMapper {

    @Autowired
    private Converter converter;

    public Book mapFromBookRequestToBook(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        return bookRequest == null ? new Book() : mapBookRequestToBook(bookRequest);
    }

    private Book mapBookRequestToBook(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        Book book = getBookFromRequest(bookRequest);
        setBookParameters(book, bookRequest);
        return book;
    }

    private Book getBookFromRequest(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        return converter.mapFromRequest(bookRequest.getBook(), Book.class);
    }

    private void setBookParameters(Book book, BookRequest bookRequest)
            throws JsonMappingException, JsonProcessingException {
        book.setChapters(getChaptersFromRequest(bookRequest, book));
        book.setGenres(getGenresFromRequest(bookRequest, book));
        book.setTags(getTagsFromRequest(bookRequest, book));
    }

    private Set<Chapter> getChaptersFromRequest(BookRequest bookRequest, Book book)
            throws JsonMappingException, JsonProcessingException {
        Set<Chapter> chapters = converter.mapFromCollectionRequest(bookRequest.getChapters(), Chapter.class);
        setParametersToChapters(chapters, book);
        return chapters;
    }

    private void setParametersToChapters(Set<Chapter> chapters, Book book) {
        for (Chapter chapter : chapters) {
            chapter.setBook(book);
        }
    }

    private Set<Genre> getGenresFromRequest(BookRequest bookRequest, Book book)
            throws JsonMappingException, JsonProcessingException {
        Set<Genre> genres = converter.mapFromCollectionRequest(bookRequest.getGenres(), Genre.class);
        setParametersToGenres(genres, book);
        return genres;
    }

    private void setParametersToGenres(Set<Genre> genres, Book book) {
        for (Genre genre : genres) {
            genre.setBooks(new HashSet<Book>(Arrays.asList(book)));
        }
    }

    private Set<Tag> getTagsFromRequest(BookRequest bookRequest, Book book)
            throws JsonMappingException, JsonProcessingException {
        Set<Tag> tags = converter.mapFromCollectionRequest(bookRequest.getTags(), Tag.class);
        setParametersToTags(tags, book);
        return tags;
    }

    private void setParametersToTags(Set<Tag> tags, Book book) {
        for (Tag tag : tags) {
            tag.setBooks(new HashSet<Book>(Arrays.asList(book)));
        }
    }
}