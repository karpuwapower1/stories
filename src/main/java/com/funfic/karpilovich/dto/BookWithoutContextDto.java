package com.funfic.karpilovich.dto;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.funfic.karpilovich.repository.projection.GenreProjection;
import com.funfic.karpilovich.repository.projection.TagProjection;
import com.funfic.karpilovich.repository.projection.UserProjection;

import lombok.Data;

@Data
public class BookWithoutContextDto {
    
    private Long id;
    private String name;
    private String description;
    private EntityModel<UserProjection> user;
    private CollectionModel<EntityModel<GenreProjection>> genres;
    private CollectionModel<EntityModel<TagProjection>> tags;

}