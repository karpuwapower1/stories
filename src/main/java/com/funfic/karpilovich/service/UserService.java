package com.funfic.karpilovich.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.dto.RegistrationRequest;
import com.funfic.karpilovich.exception.ServiceException;

public interface UserService extends UserDetailsService {

    void confirmRegistration(String token) throws ServiceException;

    User getById(Long id);

    User save(RegistrationRequest registrationRequest) throws ServiceException;
}