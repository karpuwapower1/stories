package com.funfic.karpilovich.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.funfic.karpilovich.service.util.SortingType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookTablePageDto {
    private CollectionModel<EntityModel<BookWithoutContextDto>> books;
    private final List<SortingType> sorts = Arrays.asList(SortingType.values());
}