package com.funfic.karpilovich.controller.util;

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
import com.funfic.karpilovich.controller.constant.LinkRel;
import com.funfic.karpilovich.dto.BookWithoutContextDto;
import com.funfic.karpilovich.repository.projection.BookWithoutContextProjection;

@Component
public class BookWithoutContextResponseAssembler<T extends BookWithoutContextProjection> extends PageMapper
        implements RepresentationModelAssembler<T, EntityModel<BookWithoutContextDto>> {

    private static final String PAGE_QUERY_PARAM = "page";

    @Autowired
    private BookProjectionMapper bookProjectionMapper;

    @Override
    public EntityModel<BookWithoutContextDto> toModel(T entity) {
        BookWithoutContextDto response = bookProjectionMapper.mapToBookWithoutContextDto(entity);
        return EntityModel.of(response,
                linkTo(methodOn(BookController.class).getBookById(entity.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).deleteBook(entity.getId())).withRel(LinkRel.DELETE.getName()));
    }

    @Override
    public CollectionModel<EntityModel<BookWithoutContextDto>> toCollectionModel(Iterable<? extends T> entities) {
        return CollectionModel.of(toList(entities));
    }

    public CollectionModel<EntityModel<BookWithoutContextDto>> toPagedModel(Page<? extends T> entities,
            UriComponentsBuilder builder) {
        CollectionModel<EntityModel<BookWithoutContextDto>> model = PagedModel.of(toList(entities),
                createPagedMetadata(entities));
        addLinks(model, entities, builder);
        return model;
    }

    private PagedModel.PageMetadata createPagedMetadata(Page<? extends T> entities) {
        return new PageMetadata(entities.getSize(), entities.getNumber(), entities.getTotalElements());
    }

    private List<EntityModel<BookWithoutContextDto>> toList(Iterable<? extends T> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(Collectors.toList());
    }

    private void addLinks(CollectionModel<EntityModel<BookWithoutContextDto>> model, Page<? extends T> entities,
            UriComponentsBuilder builder) {
        addFirstPageLink(model, entities.getNumber(), builder);
        addPreviousPageLink(model, entities.getNumber(), builder);
        addCurrentPageLink(model, entities.getNumber(), builder);
        addNextPageLink(model, entities.getNumber(), entities.getTotalPages() - 1, builder);
        addLastPageLink(model, entities.getNumber(), entities.getTotalPages() -1, builder);
        addLinkWithoutParams(model, builder);
    }

    private void addLinkWithoutParams(CollectionModel<EntityModel<BookWithoutContextDto>> model,
            UriComponentsBuilder builder) {
        String link = builder.replaceQueryParam(PAGE_QUERY_PARAM).build().toString();
        model.add(Link.of(link).withRel(IanaLinkRelations.ABOUT));
    }

    private void addFirstPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, int pageNumber,
            UriComponentsBuilder builder) {
        if (pageNumber != 0) {
            model.add(createLink(builder, firstPageNumber, IanaLinkRelations.FIRST));
        }
    }

    private void addPreviousPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, int pageNumber,
            UriComponentsBuilder builder) {
        if (pageNumber > 0) {
            model.add(createLink(builder, pageNumber - 1, IanaLinkRelations.PREVIOUS));
        }
    }

    private void addCurrentPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, int pageNumber,
            UriComponentsBuilder builder) {
        model.add(createLink(builder, pageNumber, IanaLinkRelations.SELF));
    }

    private void addNextPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, int pageNumber,
            int totalPages, UriComponentsBuilder builder) {
        if (pageNumber < totalPages) {
            model.add(createLink(builder, pageNumber + 1, IanaLinkRelations.NEXT));
        }
    }

    private void addLastPageLink(CollectionModel<EntityModel<BookWithoutContextDto>> model, int pageNumber,
            int totalPages, UriComponentsBuilder builder) {
        if (pageNumber < totalPages) {
            model.add(createLink(builder, totalPages, IanaLinkRelations.LAST));
        }
    }

    private Link createLink(UriComponentsBuilder builder, int pageNumber, LinkRelation relation) {
        String link = builder.replaceQueryParam(PAGE_QUERY_PARAM, pageNumber).build().toString();
        return Link.of(link).withRel(relation);
    }
}