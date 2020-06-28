package com.funfic.karpilovich.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funfic.karpilovich.entity.User;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        user = user == null ? new User() : user;
        setUserParametersToModel(user, model);
        return "index";
    }

    private void setUserParametersToModel(User user, Model model) {
        model.addAttribute("userParameters", getUserParameters(user));
    }

    private Map<String, Object> getUserParameters(User user) {
        Map<String, Object> userParameters = new HashMap<>();
        userParameters.put("user", user);
        userParameters.put("books", user.getBooks());
        return userParameters;
    }
}