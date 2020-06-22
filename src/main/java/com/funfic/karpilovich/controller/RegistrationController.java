package com.funfic.karpilovich.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.funfic.karpilovich.entity.User;
import com.funfic.karpilovich.entity.VerificationToken;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.service.MailSenderService;
import com.funfic.karpilovich.service.UserService;
import com.funfic.karpilovich.service.VerificationTokenService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @GetMapping("/registration")
    public String get(Model model) {
        model.addAttribute("user", new User());
        return Page.REGISTRATION.getName();
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
            HttpServletRequest request) {
        if (!checkBindingResult(bindingResult)) {
            try {
                registerUser(user, request);
                model.addAttribute("error", "Mail with verification link was sent to your email");  //TODO REDIRECT TO PAGE
            } catch (ServiceException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        return Page.REGISTRATION.getName();
    }

    @GetMapping("/registration/activation/{token}")
    public String confirmRegistration(Model model, @PathVariable String token) {
        String address;
        try {
            userService.confirmRegistration(token);
            address = UrlBasedViewResolver.REDIRECT_URL_PREFIX + Page.USERES.getPath();
        } catch (ServiceException e) {
            model.addAttribute("error", "Invalid link");
            address = Page.REGISTRATION.getName();
        } finally {
            verificationTokenService.deleteToken(token);
        }
        return address;
    }
    
    private boolean checkBindingResult(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }
    
    private void registerUser(User user, HttpServletRequest request) throws ServiceException {
        addUser(user);
        sendTokenToUser(user.getUsername(), request.getRequestURL(), verificationTokenService.createToken(user));
    }

    private void sendTokenToUser(String email, StringBuffer url, VerificationToken token) {
        mailSenderService.sendRegistrationConfirmation(email,
                url.append("/activation/").append(token.getToken()).toString());
    }

    private void addUser(User user) throws ServiceException {
        userService.addUser(user);
    }
}