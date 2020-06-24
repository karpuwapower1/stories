package com.funfic.karpilovich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.funfic.karpilovich.entity.Book;
import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.repository.BookRepository;

@Controller
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping
    public String getUserBooks(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("books", bookRepository.findAllByUser(user.getId()));
        return "book";
    }
    
    @GetMapping(params = "id")
    public String getBookById(@RequestParam Long id, Model model) {
        Book book = bookRepository.findById(id).orElse(new Book());
        model.addAttribute("book", book);
        return "book";
    }
}
