package com.funfic.karpilovich.dto.response;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.funfic.karpilovich.dto.projection.GenreProjection;
import com.funfic.karpilovich.dto.projection.TagProjection;
import com.funfic.karpilovich.dto.projection.UserProjection;

import lombok.Data;

@Data
public class BookWithoutContextResponse {

    private Long id;
    private String name;
    private String description;
    private EntityModel<UserProjection> user;
    private CollectionModel<EntityModel<GenreProjection>> genres;
    private CollectionModel<EntityModel<TagProjection>> tags;
}