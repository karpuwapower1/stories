package com.funfic.karpilovich.controller.util.assembler;

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
import com.funfic.karpilovich.dto.projection.TagProjection;
import com.funfic.karpilovich.service.util.SortingType;

@Component
public class TagProjectionResponseAssembler<T extends TagProjection>
        implements RepresentationModelAssembler<T, EntityModel<T>> {

    private static final int FIRST_PAGE_NUMBER = 0;
    private static final String DEFAULT_ORDER = "desc";

    @Override
    public CollectionModel<EntityModel<T>> toCollectionModel(Iterable<? extends T> entities) {
        List<EntityModel<T>> tags = StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags);
    }

    @Override
    public EntityModel<T> toModel(T tag) {
        return EntityModel.of(tag, linkTo(methodOn(BookController.class).findBooksByTag(tag.getName(),
                FIRST_PAGE_NUMBER, SortingType.NONE.toString(), DEFAULT_ORDER)).withRel(LinkRel.TAG.getName()));
    }
}