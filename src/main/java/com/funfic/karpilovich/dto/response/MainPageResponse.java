package com.funfic.karpilovich.dto.response;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.funfic.karpilovich.dto.projection.UserProjection;
import com.funfic.karpilovich.repository.GenreRepository.GenreQuantity;
import com.funfic.karpilovich.repository.TagRepository.TagQuantity;

import lombok.Data;

@Data
public class MainPageResponse {

    private CollectionModel<EntityModel<TagQuantity>> tags;
    private CollectionModel<EntityModel<GenreQuantity>> genres;
    private EntityModel<UserProjection> user;
}