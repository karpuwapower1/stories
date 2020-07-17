package com.funfic.karpilovich.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.domain.Role;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.domain.VerificationToken;
import com.funfic.karpilovich.dto.RegistrationRequest;
import com.funfic.karpilovich.dto.mapper.UserMapper;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.repository.UserRepository;
import com.funfic.karpilovich.repository.VerificationTokenRepository;
import com.funfic.karpilovich.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository emailVerificationTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User confirmRegistration(String token) throws ServiceException {
        VerificationToken emailToken = emailVerificationTokenRepository.findByToken(token);
        if (emailToken == null || !isTokenActive(emailToken)) {
            throw new ServiceException();
        }
        User user = emailToken.getUser();
        activateUser(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.isPresent() ? optional.get() : new User();
    }
    
    @Override
    public User save(RegistrationRequest registrationRequest) throws ServiceException {
        validateRegistrationRequest(registrationRequest);
        User user = prepareToSaving(registrationRequest);
        return userRepository.save(user);
    }
    
    private void validateRegistrationRequest(RegistrationRequest registrationRequest) throws ServiceException {
        checkPasswords(registrationRequest.getPassword(), registrationRequest.getConfirmPassword());
        checkUserPresents(registrationRequest.getUsername());
    }

    private void checkUserPresents(String username) throws ServiceException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ServiceException("User already present");
        }
    }
    
    private User prepareToSaving(RegistrationRequest registrationRequest) {
        User user = userMapper.mapFromRegistrationRequestToUser(registrationRequest);
        setDefaultParametersToUser(user);
        return user; 
    }

    private void checkPasswords(String password, String confirmation) throws ServiceException {
        if (!password.equals(confirmation)) {
            throw new ServiceException("Passwords do not match");
        }
    }

    private void setDefaultParametersToUser(User user) {
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
    }

    private boolean isTokenActive(VerificationToken token) {
        return token.getTerminationDate().getTime() > Calendar.getInstance().getTimeInMillis();
    }
    
    private void activateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
}