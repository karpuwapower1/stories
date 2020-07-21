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
import com.funfic.karpilovich.controller.constant.LinkRel;
import com.funfic.karpilovich.repository.projection.TagProjection;

@Component
public class TagProjectionResponseAssembler<T extends TagProjection> extends PageMapper
        implements RepresentationModelAssembler<T, EntityModel<T>> {

    @Override
    public CollectionModel<EntityModel<T>> toCollectionModel(Iterable<? extends T> entities) {
        List<EntityModel<T>> tags = StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags);
    }

    @Override
    public EntityModel<T> toModel(T tag) {
        return EntityModel.of(tag, linkTo(methodOn(BookController.class).findBooksByTag(tag.getName(), firstPageNumber))
                .withRel(LinkRel.TAG.getName()));
    }
}