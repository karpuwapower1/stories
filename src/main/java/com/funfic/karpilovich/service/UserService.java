package com.funfic.karpilovich.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.projection.UserProjection;
import com.funfic.karpilovich.dto.request.RegistrationRequest;
import com.funfic.karpilovich.dto.request.UserRequest;

public interface UserService extends UserDetailsService {

    User confirmRegistration(String token);

    UserProjection getById(Long id);
    
    UserProjection getByUsername(String username);

    User save(RegistrationRequest registrationRequest);

    Page<UserProjection> getAll(Integer page);

    void deleteUser(Long id);
    
    void updateUser(Long id, UserRequest userRequest);
}