package com.funfic.karpilovich.service;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.repository.BookRepository;

@SpringBootTest
public class BookServiceTest {
    
    @Mock
    private BookRepository bookRepository;
    
    @Autowired
    private BookService bookService;
}
