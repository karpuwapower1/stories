package com.funfic.karpilovich.dto.mapper;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.dto.BookDto;
import com.funfic.karpilovich.service.UserService;

@Component
public class BookMapper {

    private final ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Autowired
    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setup() {
        modelMapper.createTypeMap(Book.class, BookDto.class).addMappings(m -> m.skip(BookDto::setUserId))
                .setPostConverter(toBookDtoConverter());
        modelMapper.createTypeMap(BookDto.class, Book.class).addMappings(m -> m.skip(Book::setUser))
                .setPostConverter(toBookConverter());
    }

    public Book mapToBook(BookDto bookDto) {
        return bookDto == null ? new Book() : modelMapper.map(bookDto, Book.class);
    }

    public BookDto mapToDto(Book book) {
        return book == null ? new BookDto() : modelMapper.map(book, BookDto.class);
    }

    private Converter<Book, BookDto> toBookDtoConverter() {
        return context -> {
            Book source = context.getSource();
            BookDto destination = context.getDestination();
            destination.setUserId(source.getUser().getId());
            return destination;
        };
    }

    private Converter<BookDto, Book> toBookConverter() {
        return context -> setUserToBook(context.getDestination(), context.getSource());
    }

    private Book setUserToBook(Book destination, BookDto source) {
        destination.setUser(userService.getById(source.getUserId()));
        return destination;
    }
}