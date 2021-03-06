package com.funfic.karpilovich.service.impl;

import java.util.Calendar;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.funfic.karpilovich.domain.Role;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.domain.VerificationToken;
import com.funfic.karpilovich.dto.projection.UserProjection;
import com.funfic.karpilovich.dto.request.RegistrationRequest;
import com.funfic.karpilovich.dto.request.UserRequest;
import com.funfic.karpilovich.exception.BadRequestException;
import com.funfic.karpilovich.exception.ForbiddenException;
import com.funfic.karpilovich.exception.ResourceNotFoundException;
import com.funfic.karpilovich.repository.UserRepository;
import com.funfic.karpilovich.repository.VerificationTokenRepository;
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

    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException(username));
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
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException());
    }

    @Override
    public User save(RegistrationRequest registrationRequest) {
        validateRegistrationRequest(registrationRequest);
        User user = prepareToSaving(registrationRequest);
        return userRepository.save(user);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public Page<UserProjection> getAll(Integer page) {
        return userRepository.findBy(PageRequest.of(page, DEFAULT_PAGE_SIZE));
    }

    @Override
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    public void updateUser(Long id, UserRequest userRequest) {
        try {
            checkActionPermition(id);
            update(id, userRequest);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    public void deleteUser(Long id) {
        checkActionPermition(id);
        userRepository.deleteById(id);
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
        user.setRoles(Collections.singleton(Role.ROLE_USER));
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

    private void update(Long id, UserRequest request) throws JsonMappingException, JsonProcessingException {
        User user = userMapper.mapFromUserRequestToUser(request);
        User userFromDb = userRepository.findById(id).orElseThrow(() -> new BadRequestException());
        setNewParameters(user, userFromDb);
        userRepository.save(userFromDb);
    }

    private void setNewParameters(User user, User userFromDb) {
        userFromDb.setLastName(user.getLastName() == null ? userFromDb.getLastName() : user.getLastName());
        userFromDb.setFirstName(user.getFirstName() == null ? userFromDb.getFirstName() : user.getFirstName());
        userFromDb.setRoles(user.getRoles() == null ? userFromDb.getRoles() : user.getRoles());
        userFromDb.setEnabled(user.isEnabled());
        userFromDb.setPassword(user.getPassword() == null ? userFromDb.getPassword()
                : bCryptPasswordEncoder.encode(user.getPassword()));
    }

    private void checkActionPermition(Long id) {
        if (!isCurrentUserAdmin() & !isCurrentUserItself(id)) {
            throw new ForbiddenException();
        }
    }

    private boolean isCurrentUserAdmin() {
        return getAuthentication().getAuthorities().contains(Role.ROLE_ADMIN);
    }

    private boolean isCurrentUserItself(Long id) {
        return findById(id).getUsername().equals(getAuthentication().getName());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}