package com.funfic.karpilovich.dto.mapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funfic.karpilovich.domain.Chapter;
import com.funfic.karpilovich.dto.BookRequest;

@Component
public class ChapterMapper {
    
    private static final String JSON_DELIMITER = ",";
       
    public List<Chapter> mapFromBookRequestToChapter(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        return bookRequest == null ? Collections.emptyList() : mapBookRequestToChapter(bookRequest);
    }
    
    private List<Chapter> mapBookRequestToChapter(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String chapters = getChaptersStringFromBookRequest(bookRequest);
        return Arrays.asList(objectMapper.readValue(chapters, Chapter[].class));
    }
    
    private String getChaptersStringFromBookRequest(BookRequest bookRequest) {
        String chapters =  bookRequest.getChapters().stream().collect(Collectors.joining(JSON_DELIMITER));
        return new String(chapters.getBytes(), StandardCharsets.UTF_8);
    }
}