package com.funfic.karpilovich.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.domain.VerificationToken;
import com.funfic.karpilovich.dto.request.LoginRequest;
import com.funfic.karpilovich.dto.request.RegistrationRequest;
import com.funfic.karpilovich.dto.request.TokenRequest;
import com.funfic.karpilovich.dto.response.JwtResponse;
import com.funfic.karpilovich.service.MailSenderService;
import com.funfic.karpilovich.service.UserService;
import com.funfic.karpilovich.service.VerificationTokenService;
import com.funfic.karpilovich.service.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid RegistrationRequest registrationRequest, HttpServletRequest request) {
        registerUser(registrationRequest, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activation")
    public ResponseEntity<?> confirmRegistration(@Valid TokenRequest token) {
        return confirmToken(token.getToken());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid LoginRequest loginRequest) {
        try {
            return authoriseUser(loginRequest);
        } catch (DisabledException | BadCredentialsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            request.logout();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private String authenticate(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(authentication);
    }

    private void registerUser(RegistrationRequest registrationRequest, HttpServletRequest request) {
        User user = userService.save(registrationRequest);
        sendTokenToUser(user.getUsername(), verificationTokenService.createToken(user));
    }

    private void sendTokenToUser(String email, VerificationToken token) {
        mailSenderService.sendRegistrationConfirmation(email, token.getToken());
    }

    private ResponseEntity<?> confirmToken(String token) {
        userService.confirmRegistration(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private ResponseEntity<?> authoriseUser(LoginRequest loginRequest) {
        String jwt = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}