package com.funfic.karpilovich.controller.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.BookController;
import com.funfic.karpilovich.repository.projection.TagProjection;

@Component
public class TagProjectionResponseAssembler implements RepresentationModelAssembler<TagProjection, EntityModel<TagProjection>> {

    @Override
    public CollectionModel<EntityModel<TagProjection>> toCollectionModel(Iterable<? extends TagProjection> entities) {
        List<EntityModel<TagProjection>> tags = StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(Collectors.toList());
        return  CollectionModel.of(tags);
    }

    @Override
    public EntityModel<TagProjection> toModel(TagProjection tag) {
        return EntityModel.of(tag, linkTo(methodOn(BookController.class).findBooksByTag(tag.getName())).withRel("tag"));
    }
}