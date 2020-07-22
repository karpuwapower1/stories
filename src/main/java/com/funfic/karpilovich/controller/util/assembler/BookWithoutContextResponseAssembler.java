package com.funfic.karpilovich.controller.util.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.funfic.karpilovich.controller.BookController;
import com.funfic.karpilovich.controller.util.mapper.BookProjectionMapper;
import com.funfic.karpilovich.dto.BookWithoutContextDto;
import com.funfic.karpilovich.repository.projection.BookWithoutContextProjection;

@Component
public class BookWithoutContextResponseAssembler<T extends BookWithoutContextProjection>
        implements RepresentationModelAssembler<T, EntityModel<BookWithoutContextDto>> {

    private static final String PAGE_QUERY_PARAM = "page";
    private static final String SORT_QUERY_PARAM = "sort";
    private static final int FIRST_PAGE_NUMBER = 0;

    @Autowired
    private BookProjectionMapper bookProjectionMapper;

    @Override
    public EntityModel<BookWithoutContextDto> toModel(T entity) {
        BookWithoutContextDto response = bookProjectionMapper.mapToBookWithoutContextDto(entity);
        return EntityModel.of(response,
                linkTo(methodOn(BookController.class).getBookById(entity.getId())).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<BookWithoutContextDto>> toCollectionModel(Iterable<? extends T> entities) {
        return CollectionModel.of(toList(entities));
    }

    public CollectionModel<EntityModel<BookWithoutContextDto>> toPagedModel(Page<? extends T> entities,
            UriComponentsBuilder builder) {
        CollectionModel<EntityModel<BookWithoutContextDto>> model = PagedModel.of(toList(entities),
                createPagedMetadata(entities));
        addPaginationLinks(model, entities, builder);
        return model;
    }

    private PagedModel.PageMetadata createPagedMetadata(Page<? extends T> entities) {
        return new PageMetadata(entities.getSize(), entities.getNumber(), entities.getTotalElements());
    }

    private List<EntityModel<BookWithoutContextDto>> toList(Iterable<? extends T> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(Collectors.toList());
    }

    private void addPaginationLinks(CollectionModel<EntityModel<BookWithoutContextDto>> model,
            Page<? extends T> entities, UriComponentsBuilder builder) {
        addFirstPageLink(model, entities, builder);
        addPreviousPageLink(model, entities, builder);
        addSelfLink(model, builder);
        addNextPageLink(model, entities, builder);
        addLastPageLink(model, entities, builder);
    }

    private void addSelfLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, UriComponentsBuilder builder) {
        String link = builder.replaceQueryParam(PAGE_QUERY_PARAM).replaceQueryParam(SORT_QUERY_PARAM).build()
                .toString();
        model.add(Link.of(link).withRel(IanaLinkRelations.ABOUT));
    }

    private void addFirstPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, Page<? extends T> entities,
            UriComponentsBuilder builder) {
        if (!entities.isFirst()) {
            model.add(createLink(builder, FIRST_PAGE_NUMBER, IanaLinkRelations.FIRST));
        }
    }

    private void addPreviousPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model,
            Page<? extends T> entities, UriComponentsBuilder builder) {
        if (!entities.isFirst()) {
            model.add(createLink(builder, entities.getNumber() - 1, IanaLinkRelations.PREVIOUS));
        }
    }

    private void addNextPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, Page<? extends T> entities,
            UriComponentsBuilder builder) {
        if (entities.isLast()) {
            model.add(createLink(builder, entities.getNumber() + 1, IanaLinkRelations.NEXT));
        }
    }

    private void addLastPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, Page<? extends T> entities,
            UriComponentsBuilder builder) {
        if (entities.isLast()) {
            model.add(createLink(builder, entities.getTotalPages() - 1, IanaLinkRelations.LAST));
        }
    }

    private Link createLink(UriComponentsBuilder builder, int pageNumber, LinkRelation relation) {
        String link = builder.replaceQueryParam(PAGE_QUERY_PARAM, pageNumber).build().toString();
        return Link.of(link).withRel(relation);
    }
}