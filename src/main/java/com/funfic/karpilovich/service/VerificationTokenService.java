package com.funfic.karpilovich.service;

import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.entity.VerificationToken;

public interface VerificationTokenService {
    
    VerificationToken createToken(User user);
    
    void deleteToken(String token);  
}