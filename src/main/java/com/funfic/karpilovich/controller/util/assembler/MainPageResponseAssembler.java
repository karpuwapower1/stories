package com.funfic.karpilovich.controller.util.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.BookController;
import com.funfic.karpilovich.controller.MainController;
import com.funfic.karpilovich.controller.RegistrationController;
import com.funfic.karpilovich.controller.constant.LinkRel;
import com.funfic.karpilovich.dto.MainPageDto;
import com.funfic.karpilovich.service.util.SortingType;

@Component
public class MainPageResponseAssembler<T extends MainPageDto>
        implements RepresentationModelAssembler<T, EntityModel<MainPageDto>> {
    
    private static final int FIRST_PAGE_NUMBER = 0;

    @Override
    public EntityModel<MainPageDto> toModel(T entity) {
        EntityModel<MainPageDto> response = EntityModel.of(entity);
        addLinksToResponse(response);
        return response;
    }
    
    private void addLinksToResponse(EntityModel<MainPageDto> response) {
        addRegistrationLinks(response);
        addMainPageLinks(response);
    }

    private void addMainPageLinks(EntityModel<MainPageDto> response) {
        response.add(linkTo(methodOn(MainController.class).main()).withRel(LinkRel.MAIN_PAGE.getName()))
                .add(linkTo(methodOn(BookController.class).findBook(FIRST_PAGE_NUMBER, SortingType.RAITING.toString()))
                        .withRel(LinkRel.POPULAR.getName()))
                .add(linkTo(
                        methodOn(BookController.class).findBook(FIRST_PAGE_NUMBER, SortingType.LAST_UPDATE.toString()))
                                .withRel(LinkRel.LAST_UPDATE.getName()))
                .add(linkTo(methodOn(BookController.class).addBook(null, null)).withRel(LinkRel.ADD_BOOK.getName()));
    }

    private void addRegistrationLinks(EntityModel<MainPageDto> response) {
        response.add(linkTo(methodOn(RegistrationController.class).login(null)).withRel(LinkRel.LOGIN.getName()))
                .add(linkTo(methodOn(RegistrationController.class).logout(null)).withRel(LinkRel.LOGOUT.getName()))
                .add(linkTo(methodOn(RegistrationController.class).register(null, null))
                        .withRel(LinkRel.REGISTER.getName()))
                .add(linkTo(methodOn(RegistrationController.class).confirmRegistration(null))
                        .withRel(LinkRel.ACTIVATE.getName()));
    }
}