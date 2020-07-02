package com.funfic.karpilovich.dto;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.funfic.karpilovich.projection.BookWithoutContextProjection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainPageDto {
    
    private EntityModel<UserDto> userDto;
    private CollectionModel<EntityModel<BookWithoutContextProjection>> mostPopular;
    private CollectionModel<EntityModel<BookWithoutContextProjection>> lastUpdate;
   
}