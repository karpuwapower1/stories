package com.funfic.karpilovich.service.impl;

import java.util.Calendar;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.domain.Role;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.domain.VerificationToken;
import com.funfic.karpilovich.dto.RegistrationRequest;
import com.funfic.karpilovich.exception.BadRequestException;
import com.funfic.karpilovich.exception.ResourceNotFoundException;
import com.funfic.karpilovich.repository.UserRepository;
import com.funfic.karpilovich.repository.VerificationTokenRepository;
import com.funfic.karpilovich.repository.projection.UserProjection;
import com.funfic.karpilovich.service.UserService;
import com.funfic.karpilovich.service.util.UserMapper;

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
    public User confirmRegistration(String token) {
        VerificationToken emailToken = findToken(token);
        User user = emailToken.getUser();
        activateUser(user);
        return user;
    }

    @Override
    public UserProjection getById(Long id) {
        return userRepository.findUserProjectionById(id).orElseThrow(() -> new ResourceNotFoundException());
    }
    
    @Override
    public UserProjection getByUsername(String username) {
        return userRepository.findUserProjectionByUsername(username).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public User save(RegistrationRequest registrationRequest) {
        validateRegistrationRequest(registrationRequest);
        User user = prepareToSaving(registrationRequest);
        return userRepository.save(user);
    }
    
    private VerificationToken findToken(String token) {
        VerificationToken emailToken = emailVerificationTokenRepository.findByToken(token);
        if (emailToken == null || !isTokenActive(emailToken)) {
            throw new BadRequestException();
        }
        return emailToken;
    }

    private void validateRegistrationRequest(RegistrationRequest registrationRequest) {
        checkPasswords(registrationRequest.getPassword(), registrationRequest.getConfirmPassword());
        checkUserPresents(registrationRequest.getUsername());
    }

    private void checkUserPresents(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new BadRequestException("User already present");
        }
    }

    private User prepareToSaving(RegistrationRequest registrationRequest) {
        User user = userMapper.mapFromRegistrationRequestToUser(registrationRequest);
        setDefaultParametersToUser(user);
        return user;
    }

    private void checkPasswords(String password, String confirmation) {
        if (!password.equals(confirmation)) {
            throw new BadRequestException("Passwords do not match");
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