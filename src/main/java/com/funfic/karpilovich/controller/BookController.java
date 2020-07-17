package com.funfic.karpilovich.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.controller.util.BookWithoutContextResponseAssembler;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.dto.BookWithoutContextDto;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.repository.projection.BookProjection;
import com.funfic.karpilovich.repository.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.service.BookService;
import com.funfic.karpilovich.service.UserService;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookWithoutContextResponseAssembler<BookWithoutContextProjection> assembler;

    @GetMapping("{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
        try {
            BookProjection book = bookService.findById(id);
            return ResponseEntity.ok(book);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/popular")
    public ResponseEntity<?> findMostPupular() {
        Page<BookWithoutContextProjection> books = bookService.findMostPopular();
        return createResponseEntity(books);
    }

    @GetMapping("/updated")
    public ResponseEntity<?> findLastUpdated() {
        Page<BookWithoutContextProjection> books = bookService.findLastUpdated();
        return createResponseEntity(books);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findBookByAuthor(@PathVariable("id") Long id) {
        Page<BookWithoutContextProjection> books = bookService.findByUserId(id);
        return createResponseEntity(books);
    }

    @GetMapping("/genres/{name}")
    public ResponseEntity<?> findBooksByGenre(@PathVariable("name") String name) {
        Page<BookWithoutContextProjection> books = bookService.findByGenre(name);
        return createResponseEntity(books);
    }

    @GetMapping("/tags/{name}")
    public ResponseEntity<?> findBooksByTag(@PathVariable("name") String name) {
        Page<BookWithoutContextProjection> books = bookService.findByTag(name);
        return createResponseEntity(books);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBook(@Valid BookRequest bookRequest) {
        try {
            saveBook(bookRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private User findCurrentUser() throws ServiceException {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return (User) userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new ServiceException();
        }
    }

    private void saveBook(BookRequest bookRequest) throws ServiceException {
        User user = findCurrentUser();
        bookService.addBook(bookRequest, user);
    }

    private ResponseEntity<?> createResponseEntity(Page<BookWithoutContextProjection> books) {
        CollectionModel<EntityModel<BookWithoutContextDto>> entities = assembler.toCollectionModel(books);
        return ResponseEntity.ok(entities);
    }
}