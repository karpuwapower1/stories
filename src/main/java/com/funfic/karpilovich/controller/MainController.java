package com.funfic.karpilovich.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.controller.constant.LinkRel;
import com.funfic.karpilovich.controller.util.TagProjectionResponseAssembler;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.MainPageDto;
import com.funfic.karpilovich.dto.UserDto;
import com.funfic.karpilovich.dto.mapper.UserMapper;
import com.funfic.karpilovich.repository.TagRepository;
import com.funfic.karpilovich.repository.TagRepository.TagQuantity;
import com.funfic.karpilovich.service.UserService;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TagProjectionResponseAssembler<TagQuantity> responseAssembler;
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> main() {
        EntityModel<MainPageDto> response = createResponse();
        return ResponseEntity.ok(response);
    }

    private EntityModel<MainPageDto> createResponse() {
        EntityModel<MainPageDto> response = EntityModel.of(createMainPageDto());
        return addLinksToResponse(response);
    }

    private MainPageDto createMainPageDto() {
        MainPageDto mainDto = new MainPageDto();
        return setParametersToMainPageDto(mainDto);
    }

    private MainPageDto setParametersToMainPageDto(MainPageDto mainDto) {
        mainDto.setUser(createUserEntityModel());
        mainDto.setTags(createTagsEntityModel());
        return mainDto;
    }

    private EntityModel<UserDto> createUserEntityModel() {
        UserDto userDto = getUserDto();
        EntityModel<UserDto> model = EntityModel.of(userDto);
        return userDto != null ? addLinksToUserModel(model) : model;
    }

    private UserDto getUserDto() {
        Optional<User> user = getCurrentUser();
        return mapUserToUserDto(user);
    }

    private Optional<User> getCurrentUser() {
        Authentication authentication;
        return (authentication = SecurityContextHolder.getContext().getAuthentication()) != null
                ? Optional.of((User) userService.loadUserByUsername(authentication.getName()))
                : Optional.empty();
    }

    private UserDto mapUserToUserDto(Optional<User> user) {
        return user.isPresent() ? userMapper.mapToDto(user.get()) : new UserDto();
    }

    private EntityModel<UserDto> addLinksToUserModel(EntityModel<UserDto> model) {
        model.add(linkTo(methodOn(UserController.class).getUserById(model.getContent().getId())).withSelfRel());
        return model;
    }

    private CollectionModel<EntityModel<TagQuantity>> createTagsEntityModel() {
        List<TagQuantity> tags = findTags();
        return createResponseModel(tags);
    }

    private List<TagQuantity> findTags() {
        return tagRepository.findFits100TagQuantityByTagIdNative();
    }

    private CollectionModel<EntityModel<TagQuantity>> createResponseModel(List<TagQuantity> tags) {
        return responseAssembler.toCollectionModel(tags);
    }

    private EntityModel<MainPageDto> addLinksToResponse(EntityModel<MainPageDto> response) {
        addRegistrationLinks(response);
        addMainPageLinks(response);
        return response;

    }

    private void addMainPageLinks(EntityModel<MainPageDto> response) {
        response.add(linkTo(methodOn(MainController.class).main()).withRel(LinkRel.MAIN_PAGE.getName()))
                .add(linkTo(methodOn(BookController.class).findMostPupular()).withRel(LinkRel.POPULAR.getName()))
                .add(linkTo(methodOn(BookController.class).findLastUpdated()).withRel(LinkRel.UPDATE.getName()))
                .add(linkTo(methodOn(BookController.class).addBook(null)).withRel(LinkRel.ADD_BOOK.getName()));
    }

    private void addRegistrationLinks(EntityModel<MainPageDto> response) {
        response.add(linkTo(methodOn(RegistrationController.class).login(null)).withRel(LinkRel.LOGIN.getName()))
                .add(linkTo(methodOn(RegistrationController.class).logout(null)).withRel(LinkRel.LOGOUT.getName()))
                .add(linkTo(methodOn(RegistrationController.class).register(null, null)).withRel(LinkRel.REGISTER.getName()))
                .add(linkTo(methodOn(RegistrationController.class).confirmRegistration(null)).withRel(LinkRel.ACTIVATE.getName()));
    }
}