package com.funfic.karpilovich.service.util;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

@Component
public class Converter {

    private static final String JSON_DELIMITER = ",";
    
    public <T> T mapFromRequest(String request, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
        return request == null ? mapRequest("", clazz) : mapRequest(request, clazz);
    }
    
    public <T> Set<T> mapFromCollectionRequest(Collection<String> request, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
        return request == null ? Collections.emptySet() : mapCollectionRequest(request, clazz);
    }
    
    private <T> T mapRequest(String request, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        request = encodeString(request);
        return objectMapper.readValue(request, clazz);
    }
    
    private <T> Set<T> mapCollectionRequest(Collection<String> request, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionLikeType cl = objectMapper.getTypeFactory().constructCollectionLikeType(Set.class, clazz);
        String t = getStringFromBookRequest(request);
        return objectMapper.readValue(t, cl);
    }
    
    private String getStringFromBookRequest(Collection<String> request) {
        String fullString =  request.stream().collect(Collectors.joining(JSON_DELIMITER));
        return encodeString(fullString);
    }
    
    private String encodeString(String string) {
        return new String(string.getBytes(), StandardCharsets.UTF_8);
    }
}