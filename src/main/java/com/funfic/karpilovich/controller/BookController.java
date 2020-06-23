package com.funfic.karpilovich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.repository.BookRepository;

@Controller
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping
    public String getPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("book", bookRepository.findById(1L).get());
        return "book";
    }
}
