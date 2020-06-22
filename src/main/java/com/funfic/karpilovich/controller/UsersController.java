package com.funfic.karpilovich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String getPage(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return Page.USERES.getName();
    }
}