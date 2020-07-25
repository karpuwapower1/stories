package com.funfic.karpilovich.service.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.funfic.karpilovich.domain.Role;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.request.RegistrationRequest;
import com.funfic.karpilovich.dto.request.UserRequest;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Converter converter;

    public User mapFromRegistrationRequestToUser(RegistrationRequest registrationRequest) {
        return registrationRequest == null ? new User() : modelMapper.map(registrationRequest, User.class);
    }

    public User mapFromUserRequestToUser(UserRequest userRequest) throws JsonMappingException, JsonProcessingException {
        return userRequest == null ? new User() : mapToUser(userRequest);
    }

    private User mapToUser(UserRequest userRequest) throws JsonMappingException, JsonProcessingException {
        User user = getUserFromRequest(userRequest);
        user.setRoles(getRolesFromRequest(userRequest, user));
        return user;
    }

    private User getUserFromRequest(UserRequest userRequest) throws JsonMappingException, JsonProcessingException {
        return converter.mapFromRequest(userRequest.getUser(), User.class);
    }

    private Set<Role> getRolesFromRequest(UserRequest userRequest, User user)
            throws JsonMappingException, JsonProcessingException {
        Set<Role> roles = converter.mapFromCollectionRequest(userRequest.getRoles(), Role.class);
        setUserToRoles(roles, user);
        return roles;
    }

    private void setUserToRoles(Set<Role> roles, User user) {
        for (Role role : roles) {
            role.setUsers(new HashSet<User>(Arrays.asList(user)));
        }
    }
}