package com.funfic.karpilovich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return Page.USER.getName();
    }
    
    @PostMapping
    public String updateInfo(@AuthenticationPrincipal User user, Model model) {
       return Page.USER.getName(); 
    }
}