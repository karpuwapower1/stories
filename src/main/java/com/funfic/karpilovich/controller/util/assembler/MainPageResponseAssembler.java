package com.funfic.karpilovich.controller.util.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.BookController;
import com.funfic.karpilovich.controller.MainController;
import com.funfic.karpilovich.controller.RegistrationController;
import com.funfic.karpilovich.controller.UserController;
import com.funfic.karpilovich.controller.constant.LinkRel;
import com.funfic.karpilovich.domain.Role;
import com.funfic.karpilovich.dto.response.MainPageResponse;
import com.funfic.karpilovich.service.util.SortingType;

@Component
public class MainPageResponseAssembler<T extends MainPageResponse>
        implements RepresentationModelAssembler<T, EntityModel<T>> {

    private static final int FIRST_PAGE_NUMBER = 0;
    private static final String DEFAULT_ORDER = "desc";

    @Override
    public EntityModel<T> toModel(T entity) {
        EntityModel<T> response = EntityModel.of(entity);
        addLinksToResponse(response);
        return response;
    }

    private void addLinksToResponse(EntityModel<T> response) {
        addLinksDependingOnUser(response);
        addGeneralLinks(response);
    }

    private void addLinksDependingOnUser(EntityModel<T> response) {
        if (response.getContent().getUser() != null) {
            addLinksForAuthenticatedUser(response);
            addLinksForAdmin(response);
        } else {
            addLinksForNotAuthenticatedUser(response);
        }
    }

    private void addLinksForNotAuthenticatedUser(EntityModel<T> response) {
        response.add(linkTo(methodOn(RegistrationController.class).login(null)).withRel(LinkRel.LOGIN.getName()))
                .add(linkTo(methodOn(RegistrationController.class).register(null, null))
                        .withRel(LinkRel.REGISTER.getName()))
                .add(linkTo(methodOn(RegistrationController.class).confirmRegistration(null))
                        .withRel(LinkRel.ACTIVATE.getName()));
    }

    private void addLinksForAuthenticatedUser(EntityModel<T> response) {
        response.add(linkTo(methodOn(RegistrationController.class).logout(null)).withRel(LinkRel.LOGOUT.getName()))
                .add(linkTo(methodOn(BookController.class).addBook(null, null)).withRel(LinkRel.ADD_BOOK.getName()))
                .add(linkTo(methodOn(BookController.class).findBookByAuthor(
                        response.getContent().getUser().getContent().getId(), FIRST_PAGE_NUMBER,
                        SortingType.DATE_POSTED.toString(), DEFAULT_ORDER)).withRel(LinkRel.HOME.getName()));
    }

    private void addGeneralLinks(EntityModel<T> response) {
        response.add(linkTo(methodOn(MainController.class).main()).withRel(LinkRel.MAIN_PAGE.getName()))
                .add(linkTo(methodOn(BookController.class).findBooks(FIRST_PAGE_NUMBER, SortingType.RAITING.toString(),
                        DEFAULT_ORDER)).withRel(LinkRel.POPULAR.getName()))
                .add(linkTo(methodOn(BookController.class).findBooks(FIRST_PAGE_NUMBER,
                        SortingType.LAST_UPDATE.toString(), DEFAULT_ORDER)).withRel(LinkRel.LAST_UPDATE.getName()));
    }
    
    private void addLinksForAdmin(EntityModel<T> response) {
        if (response.getContent().getUser().getContent().getRoles().contains(Role.ROLE_ADMIN)) {
            response.add(linkTo(methodOn(UserController.class).findAll(FIRST_PAGE_NUMBER)).withRel(LinkRel.ALL_USERS.getName()));
        }
    }
}