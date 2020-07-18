package com.funfic.karpilovich.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.domain.VerificationToken;
import com.funfic.karpilovich.repository.VerificationTokenRepository;
import com.funfic.karpilovich.service.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    
    private static final int VERIFICATION_TOKEN_LENGTH = 10;

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
        return RandomStringUtils.randomAlphanumeric(VERIFICATION_TOKEN_LENGTH);
    }
}