package com.funfic.karpilovich.dto.mapper;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.dto.BookRequest;

@Component
public class BookMapper {
    
    public Book mapFromBookRequestToBook(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        return bookRequest == null ? new Book() : mapBookRequestToBook(bookRequest);
    }

    private Book mapBookRequestToBook(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        String book = getStringBookFromBookRequest(bookRequest);
        return new ObjectMapper().readValue(book, Book.class);
    }
    
    private String getStringBookFromBookRequest(BookRequest bookRequest) {
        return new String(bookRequest.getBook().getBytes(), StandardCharsets.UTF_8);
    } 
}