package com.funfic.karpilovich.dto.mapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.Chapter;
import com.funfic.karpilovich.domain.Genre;
import com.funfic.karpilovich.dto.BookRequest;

@Component
public class BookMapper {

    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private GenreMapper genreMapper;

    public Book mapFromBookRequestToBook(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        return bookRequest == null ? new Book() : mapBookRequestToBook(bookRequest);
    }

    private Book mapBookRequestToBook(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        Book book = getBookFromRequest(bookRequest);
        setBookParameters(book, bookRequest);
        return book;
    }

    private void setBookParameters(Book book, BookRequest bookRequest)
            throws JsonMappingException, JsonProcessingException {
        book.setChapters(getChaptersFromRequest(bookRequest, book));
        book.setGenres(getGenresFromRequest(bookRequest, book));
    }

    private Book getBookFromRequest(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        String book = getStringBookFromBookRequest(bookRequest);
        return new ObjectMapper().readValue(book, Book.class);
    }

    private String getStringBookFromBookRequest(BookRequest bookRequest) {
        return new String(bookRequest.getBook().getBytes(), StandardCharsets.UTF_8);
    }

    private List<Chapter> getChaptersFromRequest(BookRequest bookRequest, Book book)
            throws JsonMappingException, JsonProcessingException {
        List<Chapter> chapters = chapterMapper.mapFromBookRequestToChapter(bookRequest);
        setParametersToChapters(chapters, book);
        return chapters;
    }

    private void setParametersToChapters(List<Chapter> chapters, Book book) {
        for (int i = 0; i < chapters.size(); i++) {
            setChapterParameters(chapters.get(i), i + 1, book);
        }
    }

    private void setChapterParameters(Chapter chapter, int number, Book book) {
        chapter.setBook(book);
        chapter.setNumber((short) number);
    }

    private Set<Genre> getGenresFromRequest(BookRequest bookRequest, Book book)
            throws JsonMappingException, JsonProcessingException {
        Set<Genre> genres = genreMapper.mapFromBookRequestToGenre(bookRequest);
        setParametersToGenres(genres, book);
        return genres;
    }

    private void setParametersToGenres(Set<Genre> genres, Book book) {
        for (Genre genre : genres) {
            genre.setBooks(Arrays.asList(book));
        }
    }
}