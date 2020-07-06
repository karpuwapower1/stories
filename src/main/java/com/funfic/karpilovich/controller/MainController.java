package com.funfic.karpilovich.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.MainPageDto;
import com.funfic.karpilovich.dto.UserDto;
import com.funfic.karpilovich.dto.mapper.UserMapper;
import com.funfic.karpilovich.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.service.BookService;
import com.funfic.karpilovich.service.UserService;

@RestController
@RequestMapping("/main")
public class MainController {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public MainPageDto main() {
        Page<BookWithoutContextProjection> mostPopular = bookService.findMostPopular();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userMapper.mapToDto((User)userService.loadUserByUsername(username));
        CollectionModel<EntityModel<BookWithoutContextProjection>> populars =createBookEntityModel(mostPopular);
        return new MainPageDto(createUserDtoEntityModel(userDto), populars, null);
    }
    
    private EntityModel<UserDto> createUserDtoEntityModel(UserDto userDto) {
        
        if (userDto == null) {
            return EntityModel.of(userDto);
        }
        return EntityModel.of(userDto, linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
    }
    
    private CollectionModel<EntityModel<BookWithoutContextProjection>> createBookEntityModel(Page<BookWithoutContextProjection> books) {
        List<EntityModel<BookWithoutContextProjection>> listOfBooks 
        = books.stream().map(this::createBookWithotContextEntityModel).collect(Collectors.toList());
        return CollectionModel.of(listOfBooks);
    }
    
    private EntityModel<BookWithoutContextProjection> createBookWithotContextEntityModel(BookWithoutContextProjection book) {
        return EntityModel.of(book, linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel());
    }
}