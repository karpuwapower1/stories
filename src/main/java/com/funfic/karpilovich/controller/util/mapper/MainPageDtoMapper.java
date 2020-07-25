package com.funfic.karpilovich.controller.util.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.UserController;
import com.funfic.karpilovich.controller.util.assembler.GenreProjectionResponseAssembler;
import com.funfic.karpilovich.controller.util.assembler.TagProjectionResponseAssembler;
import com.funfic.karpilovich.dto.projection.UserProjection;
import com.funfic.karpilovich.dto.response.MainPageResponse;
import com.funfic.karpilovich.repository.GenreRepository.GenreQuantity;
import com.funfic.karpilovich.repository.TagRepository.TagQuantity;

@Component
public class MainPageDtoMapper {

    @Autowired
    private TagProjectionResponseAssembler<TagQuantity> tagResponseAssembler;
    @Autowired
    private GenreProjectionResponseAssembler<GenreQuantity> genreResponseAssembler;

    public MainPageResponse mapToMainPageDto(UserProjection user, Collection<TagQuantity> tags,
            Collection<GenreQuantity> genres) {
        MainPageResponse dto = new MainPageResponse();
        setParametersToMainPageDto(dto, user, tags, genres);
        return dto;
    }

    private void setParametersToMainPageDto(MainPageResponse mainDto, UserProjection user, Collection<TagQuantity> tags,
            Collection<GenreQuantity> genres) {
        setUserParameter(mainDto, user);
        mainDto.setTags(createTagsEntityModel(tags));
        mainDto.setGenres(createGenresEntityModel(genres));
    }

    private void setUserParameter(MainPageResponse mainDto, UserProjection user) {
        if (user != null) {
            mainDto.setUser(createUserEntityModel(user));
        }
    }

    private EntityModel<UserProjection> createUserEntityModel(UserProjection user) {
        EntityModel<UserProjection> model = EntityModel.of(user);
        addLinksToUserModel(model);
        return model;
    }

    private void addLinksToUserModel(EntityModel<UserProjection> model) {
        model.add(linkTo(methodOn(UserController.class).getUserById(model.getContent().getId())).withSelfRel());
    }

    private CollectionModel<EntityModel<TagQuantity>> createTagsEntityModel(Collection<TagQuantity> tags) {
        return tagResponseAssembler.toCollectionModel(tags);
    }

    private CollectionModel<EntityModel<GenreQuantity>> createGenresEntityModel(Collection<GenreQuantity> genres) {
        return genreResponseAssembler.toCollectionModel(genres);
    }
}