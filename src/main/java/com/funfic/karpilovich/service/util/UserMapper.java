package com.funfic.karpilovich.service.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.RegistrationRequest;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public User mapFromRegistrationRequestToUser(RegistrationRequest registrationRequest) {
        return registrationRequest == null ? new User() : modelMapper.map(registrationRequest, User.class);
    }
}