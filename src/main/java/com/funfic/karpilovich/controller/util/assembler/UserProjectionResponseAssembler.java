package com.funfic.karpilovich.controller.util.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.BookController;
import com.funfic.karpilovich.controller.UserController;
import com.funfic.karpilovich.controller.constant.LinkRel;
import com.funfic.karpilovich.dto.projection.UserProjection;
import com.funfic.karpilovich.service.util.SortingType;

@Component
public class UserProjectionResponseAssembler<T extends UserProjection>
        implements RepresentationModelAssembler<T, EntityModel<T>> {

    private static final int FIRST_PAGE_NUMBER = 0;
    private static final String DEFAULT_ORDER = "desc";

    @Override
    public EntityModel<T> toModel(T user) {
        return EntityModel.of(user, linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                linkTo((methodOn(BookController.class)).findBookByAuthor(user.getId(), FIRST_PAGE_NUMBER,
                        SortingType.NONE.toString(), DEFAULT_ORDER)).withRel(LinkRel.AUTHOR.getName()));
    }

    @Override
    public CollectionModel<EntityModel<T>> toCollectionModel(Iterable<? extends T> entities) {
        return CollectionModel.of(toList(entities));
    }

    public CollectionModel<EntityModel<T>> toPagedModel(Page<? extends T> page) {
        CollectionModel<EntityModel<T>> entities = PagedModel.of(toList(page), getPageMetadata(page));
        addPaginationLinks(entities, page);
        return entities;
    }

    private List<EntityModel<T>> toList(Iterable<? extends T> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(Collectors.toList());
    }

    private PagedModel.PageMetadata getPageMetadata(Page<? extends T> entities) {
        return new PageMetadata(entities.getSize(), entities.getNumber(), entities.getTotalElements(),
                entities.getTotalPages());
    }

    private void addPaginationLinks(CollectionModel<EntityModel<T>> entities, Page<? extends T> users) {
        addFirstPageLink(users, entities);
        addPreviousLink(users, entities);
        addSelfLink(users, entities);
        addNextPageLink(users, entities);
        addLastPageLink(users, entities);
    }

    private void addFirstPageLink(Page<? extends T> users, CollectionModel<EntityModel<T>> entities) {
        if (!users.isFirst()) {
            entities.add(
                    linkTo(methodOn(UserController.class).findAll(FIRST_PAGE_NUMBER)).withRel(IanaLinkRelations.FIRST));
        }
    }

    private void addPreviousLink(Page<? extends T> users, CollectionModel<EntityModel<T>> entities) {
        if (!users.isFirst()) {
            entities.add(linkTo(methodOn(UserController.class).findAll(users.getNumber() - 1))
                    .withRel(IanaLinkRelations.PREVIOUS));
        }
    }

    private void addSelfLink(Page<? extends T> users, CollectionModel<EntityModel<T>> entities) {
        entities.add(linkTo(methodOn(UserController.class).findAll(users.getNumber())).withSelfRel());
    }

    private void addNextPageLink(Page<? extends T> users, CollectionModel<EntityModel<T>> entities) {
        if (!users.isLast()) {
            entities.add(linkTo(methodOn(UserController.class).findAll(users.getNumber() + 1))
                    .withRel(IanaLinkRelations.NEXT));
        }
    }

    private void addLastPageLink(Page<? extends T> users, CollectionModel<EntityModel<T>> entities) {
        if (!users.isLast()) {
            entities.add(linkTo(methodOn(UserController.class).findAll(users.getTotalPages() - 1))
                    .withRel(IanaLinkRelations.LAST));
        }
    }
}