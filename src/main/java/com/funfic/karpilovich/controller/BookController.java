package com.funfic.karpilovich.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.repository.BookRepository;
import com.funfic.karpilovich.service.BookService;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookService bookService;

    @GetMapping
    public Iterable<Book> getUserBooks(@AuthenticationPrincipal User user) {
        return bookRepository.findAll();
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable("id") Long id) {
        return bookRepository.findByIdOrderByChaptersNumber(id).orElse(new Book());
    }
    
    @GetMapping("/popular")
    public ResponseEntity<?> findMostPupular() {
        Page<BookWithoutContextProjection> books = bookService.findMostPopular();
        CollectionModel<EntityModel<BookWithoutContextProjection>> entities = createBookEntityModel(books);
        return ResponseEntity.ok(entities);
    }
    
    @GetMapping("/updated") 
    public ResponseEntity<?> findLastUpdated() {
        Page<BookWithoutContextProjection> books = bookService.findLastUpdated();
        CollectionModel<EntityModel<BookWithoutContextProjection>> entities = createBookEntityModel(books);
        return ResponseEntity.ok(entities);
    }
    
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    private CollectionModel<EntityModel<BookWithoutContextProjection>> createBookEntityModel(Page<BookWithoutContextProjection> books) {
        List<EntityModel<BookWithoutContextProjection>> listOfBooks 
        = books.stream().map(this::createBookWithotContextEntityModel).collect(Collectors.toList());
        return CollectionModel.of(listOfBooks);
    }
    
    private EntityModel<BookWithoutContextProjection> createBookWithotContextEntityModel(BookWithoutContextProjection book) {
        return EntityModel.of(book, linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel(), 
                linkTo(methodOn(BookController.class).deleteBook(book.getId())).withRel("delete"), 
                linkTo(methodOn(UserController.class).getUserById(book.getUserId())).withRel("author"));
    }
    
    @PostMapping
    public Book addBook() {
        
        return new Book();
    }
}