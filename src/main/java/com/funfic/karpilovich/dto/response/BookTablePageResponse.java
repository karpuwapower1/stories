package com.funfic.karpilovich.dto.response;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.funfic.karpilovich.service.util.SortingType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookTablePageResponse {
    
    private CollectionModel<EntityModel<BookWithoutContextResponse>> books;
    private final List<SortingType> sorts = Arrays.asList(SortingType.values());
}