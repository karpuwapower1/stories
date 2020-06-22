package com.funfic.karpilovich.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.exception.ServiceException;

public interface UserService extends UserDetailsService {

    User addUser(User user) throws ServiceException;

    void confirmRegistration(String token) throws ServiceException;
}