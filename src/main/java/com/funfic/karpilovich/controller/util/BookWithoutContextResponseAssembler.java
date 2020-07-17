package com.funfic.karpilovich.controller.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.BookController;
import com.funfic.karpilovich.dto.BookWithoutContextDto;
import com.funfic.karpilovich.repository.projection.BookWithoutContextProjection;

@Component
public class BookWithoutContextResponseAssembler<T extends BookWithoutContextProjection>
        implements RepresentationModelAssembler<T, EntityModel<BookWithoutContextDto>> {

    @Autowired
    private BookProjectionMapper bookProjectionMapper;

    @Override
    public EntityModel<BookWithoutContextDto> toModel(T entity) {
        BookWithoutContextDto response = bookProjectionMapper.mapToBookWithoutContextDto(entity);
        return EntityModel.of(response,
                linkTo(methodOn(BookController.class).getBookById(entity.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).deleteBook(entity.getId())).withRel("delete"));
    }

    @Override
    public CollectionModel<EntityModel<BookWithoutContextDto>> toCollectionModel(
            Iterable<? extends T> entities) {
        List<EntityModel<BookWithoutContextDto>> books = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(books);
    }
}