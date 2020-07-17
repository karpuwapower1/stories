package com.funfic.karpilovich.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.service.MailSenderService;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String from;

    @Override
    public void sendRegistrationConfirmation(String to, String token) {
        
        mailSender.send(createMessage(from, to, "Registration confirmation",
                "Confirm your email by passing a link \n\r" + token));
    }
    
    public void sendRegisrationSuccess(String to) {
        mailSender.send(createMessage(from, to, "Registration confirmation", "Registration success"));
    }

    private SimpleMailMessage createMessage(String from, String to, String subject,
            String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}