package com.funfic.karpilovich.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.entity.VerificationToken;
import com.funfic.karpilovich.repository.VerificationTokenRepository;
import com.funfic.karpilovich.service.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository repository;

    @Override
    public VerificationToken createToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user, createToken());
        return repository.save(verificationToken);
    }

    @Override
    public void deleteToken(String token) {
        repository.delete(repository.findByToken(token));
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }
}