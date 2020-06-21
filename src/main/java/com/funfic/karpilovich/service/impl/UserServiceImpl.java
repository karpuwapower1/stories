package com.funfic.karpilovich.service.impl;

import java.util.Collections;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.entity.Role;
import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.repository.RoleRepository;
import com.funfic.karpilovich.repository.UserRepository;
import com.funfic.karpilovich.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() ? user.get() : new User();
    }
    
    @Override
    public User addUser(User user) throws ServiceException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ServiceException("User already present");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new ServiceException("Passwords do not match");
        }
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}