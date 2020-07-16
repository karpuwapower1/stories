package com.funfic.karpilovich.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.RegistrationRequest;
import com.funfic.karpilovich.dto.UserDto;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
    
    public User mapFromRegistrationRequestToUser(RegistrationRequest registrationRequest) {
        return registrationRequest == null ? new User() : modelMapper.map(registrationRequest, User.class);
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