package com.funfic.karpilovich.service;

public interface MailSenderService {
    
    void sendRegistrationConfirmation(String to, String token);
}