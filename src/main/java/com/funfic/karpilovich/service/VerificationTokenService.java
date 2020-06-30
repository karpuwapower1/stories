package com.funfic.karpilovich.service;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.domain.VerificationToken;

public interface VerificationTokenService {
    
    VerificationToken createToken(User user);
    
    void deleteToken(String token);  
}