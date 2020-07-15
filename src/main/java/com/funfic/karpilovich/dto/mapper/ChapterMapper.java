package com.funfic.karpilovich.dto.mapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funfic.karpilovich.domain.Chapter;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.dto.ChapterDto;

@Component
public class ChapterMapper {
    
    @Autowired
    private ModelMapper modelMapper;
       
    public List<Chapter> mapFromBookRequestToCjapter(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        return bookRequest == null ? Collections.emptyList() : mapBookRequestToChapter(bookRequest);
    }
    
    private List<Chapter> mapBookRequestToChapter(BookRequest bookRequest) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ChapterDto> chapters = takeChapterDtoFromBookRequest(bookRequest, objectMapper);
        return chapters.stream().map(chapter -> modelMapper.map(chapter, Chapter.class)).collect(Collectors.toList());
    }

    private List<ChapterDto> takeChapterDtoFromBookRequest(BookRequest bookRequest, ObjectMapper objectMapper)
            throws JsonMappingException, JsonProcessingException {
        String chapters = bookRequest.getChapters().stream().collect(Collectors.joining(","));
        ChapterDto[] chapter = objectMapper.readValue(new String(chapters.getBytes(), StandardCharsets.UTF_8),
                ChapterDto[].class);
        return Arrays.asList(chapter);
    }
}