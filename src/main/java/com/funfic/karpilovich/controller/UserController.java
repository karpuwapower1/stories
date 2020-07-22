package com.funfic.karpilovich.controller;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.repository.projection.UserProjection;
import com.funfic.karpilovich.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) throws ServiceException {
       UserProjection user = userService.getById(id);
       return ResponseEntity.ok(user);
    }
}