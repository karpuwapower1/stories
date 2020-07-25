package com.funfic.karpilovich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.controller.util.assembler.UserProjectionResponseAssembler;
import com.funfic.karpilovich.dto.projection.UserProjection;
import com.funfic.karpilovich.dto.request.UserRequest;
import com.funfic.karpilovich.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserProjectionResponseAssembler<UserProjection> assembler;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        Page<UserProjection> users = userService.getAll(page);
        return ResponseEntity.ok(prepareResponse(users));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        UserProjection user = userService.getById(id);
        return ResponseEntity.ok(assembler.toModel(user));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, UserRequest user) {
        userService.updateUser(id, user);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    private CollectionModel<EntityModel<UserProjection>> prepareResponse(Page<UserProjection> users) {
        return assembler.toPagedModel(users);
    }

}