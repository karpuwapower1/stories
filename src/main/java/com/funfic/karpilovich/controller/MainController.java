package com.funfic.karpilovich.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.UserDto;
import com.funfic.karpilovich.dto.mapper.UserMapper;
import com.funfic.karpilovich.service.BookService;
import com.funfic.karpilovich.service.UserService;

@RestController
@RequestMapping("/main")
public class MainController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> main() {
        return ResponseEntity.ok().body(createMainPageEntityModel());
    }
    
    private EntityModel<UserDto> createMainPageEntityModel() {
        EntityModel<UserDto> entityModel = createUserEntityModel();
        entityModel.add(linkTo(methodOn(BookController.class).findMostPupular()).withRel("popular"));
        entityModel.add(linkTo(methodOn(BookController.class).findLastUpdated()).withRel("update"));
        return entityModel;
    }
    
    private EntityModel<UserDto> createUserEntityModel() {  
        return isAuthenticated() ? createAuthentificatedEntityModel() : createNotAuthentificatedEntityModel();
    }
    
    private boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
    
    private EntityModel<UserDto> createNotAuthentificatedEntityModel() {
        return EntityModel.of(new UserDto());
    }

    private EntityModel<UserDto> createAuthentificatedEntityModel() {
        String username = getUsername();
        UserDto userDto = userMapper.mapToDto((User) userService.loadUserByUsername(username));
        return EntityModel.of(userDto, linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}