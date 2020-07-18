package com.funfic.karpilovich.controller.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.BookController;
import com.funfic.karpilovich.repository.projection.UserProjection;

@Component
public class UserProjectionResponseAssembler<T extends UserProjection>
        implements RepresentationModelAssembler<T, EntityModel<T>> {

    @Override
    public EntityModel<T> toModel(T user) {
        return EntityModel.of(user,
                linkTo((methodOn(BookController.class)).findBookByAuthor(user.getId())).withRel("author"));
    }
}