package com.funfic.karpilovich.dto;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.funfic.karpilovich.repository.GenreRepository.GenreQuantity;
import com.funfic.karpilovich.repository.TagRepository.TagQuantity;

import lombok.Data;

@Data
public class MainPageDto {

    private CollectionModel<EntityModel<TagQuantity>> tags;
    private CollectionModel<EntityModel<GenreQuantity>> genres;
    private EntityModel<UserDto> user;
}