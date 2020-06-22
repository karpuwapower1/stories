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
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        mailSender.send(createMessage(simpleMailMessage, from, to, "Registration confirmation",
                "Confirm your email by passing a link \n\r" + token));
    }

    private SimpleMailMessage createMessage(SimpleMailMessage message, String from, String to, String subject,
            String text) {
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}