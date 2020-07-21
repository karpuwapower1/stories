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
import com.funfic.karpilovich.repository.projection.GenreProjection;

@Component
public class GenreProjectionResponseAssembler<T extends GenreProjection> extends PageMapper
        implements RepresentationModelAssembler<T, EntityModel<T>> {

    @Override
    public CollectionModel<EntityModel<T>> toCollectionModel(Iterable<? extends T> entities) {
        List<EntityModel<T>> genres = StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(genres);
    }

    @Override
    public EntityModel<T> toModel(T genre) {
        return EntityModel.of(genre,
                linkTo(methodOn(BookController.class).findBooksByGenre(genre.getName(), firstPageNumber))
                        .withRel(LinkRel.GENRE.getName()));
    }
}