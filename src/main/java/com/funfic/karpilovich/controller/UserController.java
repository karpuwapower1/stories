package com.funfic.karpilovich.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDto getUser(@AuthenticationPrincipal User user) {
        return userMapper.mapToDto(user);
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable("id") User user) {
        return userMapper.mapToDto(user);
    }

    @PutMapping("{id}")
    public String updateInfo(@PathVariable("id") User userFromDb) {
        // todo
        return Page.USER.getName();
    }
}