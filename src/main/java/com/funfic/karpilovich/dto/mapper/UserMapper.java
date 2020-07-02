package com.funfic.karpilovich.dto.mapper;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.BookDto;
import com.funfic.karpilovich.dto.UserDto;
import com.funfic.karpilovich.repository.BookRepository;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final BookMapper bookMapper;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    public UserMapper(ModelMapper modelMapper, BookMapper bookMapper) {
        this.modelMapper = modelMapper;
        this.bookMapper = bookMapper;
    }

//    /*
//     * @PostConstruct public void setup() { modelMapper.createTypeMap(User.class,
//     * UserDto.class).addMappings(m -> m.skip(UserDto::setBooks))
//     * .setPostConverter(toUserDto()); }
//     */

    public User mapToUser(UserDto userDto) {
        return userDto == null ? new User() : modelMapper.map(userDto, User.class);
    }

    public UserDto mapToDto(User user) {
        return user == null ? new UserDto() : modelMapper.map(user, UserDto.class);
    }

//    private Converter<User, UserDto> toUserDto() {
//        return context -> {
//            User source = context.getSource();
//            UserDto destination = context.getDestination();
//
//            Set<BookDto> books = new HashSet<>();
//            for (Book book : bookRepository.findAll(Pageable.unpaged())) {
//                books.add(bookMapper.mapToDto(book));
//            }
//            destination.setBooks(books);
//
//            return destination;
//        };
//    }
}