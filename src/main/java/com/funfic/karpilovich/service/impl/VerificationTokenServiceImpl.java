package com.funfic.karpilovich.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.domain.VerificationToken;
import com.funfic.karpilovich.repository.VerificationTokenRepository;
import com.funfic.karpilovich.service.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository repository;

    @Override
    public VerificationToken createToken(User user) {
        return repository.save(new VerificationToken(user, createToken()));
    }

    @Override
    public void deleteToken(String token) {
        repository.delete(repository.findByToken(token));
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }
}