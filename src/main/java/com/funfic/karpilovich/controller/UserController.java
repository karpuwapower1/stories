package com.funfic.karpilovich.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.UserDto;
import com.funfic.karpilovich.dto.mapper.UserMapper;
import com.funfic.karpilovich.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public EntityModel<UserDto> getUser(@AuthenticationPrincipal User user) {
        return EntityModel.of(userMapper.mapToDto(user),
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
       return createResponseEntity(user);
    }

    @PutMapping("{id}")
    public String updateInfo(@PathVariable("id") User userFromDb) {
        // todo
        return Page.USER.getName();
    }
    
    private ResponseEntity<?> createResponseEntity(User user) {
        if (user == null || user.getId() == null) {
            return ResponseEntity.badRequest().body(Problem.create().withTitle("Invalid link"));
        }
        EntityModel<UserDto> entityModel = EntityModel.of(userMapper.mapToDto(userService.getById(user.getId())), 
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        return ResponseEntity.ok().body(entityModel);
    }
}