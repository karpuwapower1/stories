package com.funfic.karpilovich.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.RegistrationRequest;
import com.funfic.karpilovich.repository.projection.UserProjection;

public interface UserService extends UserDetailsService {

    User confirmRegistration(String token);

    UserProjection getById(Long id);
    
    UserProjection getByUsername(String username);

    User save(RegistrationRequest registrationRequest);
}