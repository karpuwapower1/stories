package com.funfic.karpilovich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public User getUser(@AuthenticationPrincipal User user) {
        System.out.println(user);
        return user;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") User user) {
        return user;
    }

    @PutMapping("{id}")
    public String updateInfo(@PathVariable("id") User userFromDb) {
        // todo
        return Page.USER.getName();
    }
}