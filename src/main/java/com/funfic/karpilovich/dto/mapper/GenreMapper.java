package com.funfic.karpilovich.dto.mapper;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funfic.karpilovich.domain.Genre;
import com.funfic.karpilovich.dto.BookRequest;

@Component
public class GenreMapper {
    
    private static final String JSON_DELIMITER = ",";
    
    public Set<Genre> mapFromBookRequestToGenre(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        return bookRequest == null ? Collections.emptySet() : mapBookRequestToGenre(bookRequest);
    }
    
    private Set<Genre> mapBookRequestToGenre(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        String string = getGenresStringFromBookRequest(bookRequest);
        Genre[] genres = new ObjectMapper().readValue(string, Genre[].class);
        return convertFromArrayToSet(genres);
    }
    
    private String getGenresStringFromBookRequest(BookRequest bookRequest) {
        String genres =  bookRequest.getGenres().stream().collect(Collectors.joining(JSON_DELIMITER));
        return new String(genres.getBytes(), StandardCharsets.UTF_8);
    }
    
    private HashSet<Genre> convertFromArrayToSet(Genre[] genres) {
        HashSet<Genre> set = new HashSet<>();
        Collections.addAll(set, genres);
        return set;
    }
}