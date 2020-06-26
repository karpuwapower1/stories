package com.funfic.karpilovich.service.impl;

import java.util.Calendar;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.entity.Role;
import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.entity.VerificationToken;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.repository.UserRepository;
import com.funfic.karpilovich.repository.VerificationTokenRepository;
import com.funfic.karpilovich.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository emailVerificationTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User addUser(User user) throws ServiceException {
        checkUserPresents(user);
        checkUserPassword(user.getPassword(), user.getConfirmPassword());
        setDefaultParametersToUser(user);
        return userRepository.save(user);
    }

    @Override
    public void confirmRegistration(String token) throws ServiceException {
        VerificationToken emailToken = emailVerificationTokenRepository.findByToken(token);
        if (emailToken == null || !isTokenActive(emailToken)) {
            throw new ServiceException();
        }
        activateUser(emailToken.getUser());
    }

    private void activateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    private void checkUserPresents(User user) throws ServiceException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ServiceException("User already present");
        }
    }

    private void checkUserPassword(String password, String confirmation) throws ServiceException {
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
}