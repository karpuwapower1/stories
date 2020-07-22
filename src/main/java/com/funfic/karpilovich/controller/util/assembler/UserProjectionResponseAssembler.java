package com.funfic.karpilovich.controller.util.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.BookController;
import com.funfic.karpilovich.controller.constant.LinkRel;
import com.funfic.karpilovich.repository.projection.UserProjection;
import com.funfic.karpilovich.service.util.SortingType;

@Component
public class UserProjectionResponseAssembler<T extends UserProjection>
        implements RepresentationModelAssembler<T, EntityModel<T>> {

    private static final int FIRST_PAGE_NUMBER = 0;

    @Override
    public EntityModel<T> toModel(T user) {
        return EntityModel.of(user, linkTo((methodOn(BookController.class)).findBookByAuthor(user.getId(),
                FIRST_PAGE_NUMBER, SortingType.NONE.toString())).withRel(LinkRel.AUTHOR.getName()));
    }
}