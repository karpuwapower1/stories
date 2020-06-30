package com.funfic.karpilovich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.entity.Book;
import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.repository.BookRepository;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable<Book> getUserBooks(@AuthenticationPrincipal User user) {
        return bookRepository.findAll();
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable("id") Long id) {
        return bookRepository.findById(id).orElse(new Book());
    }
    
    @PostMapping
    public Book addBook() {
        return new Book();
    }
}