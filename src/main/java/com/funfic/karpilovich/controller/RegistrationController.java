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
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.funfic.karpilovich.config.jwt.JwtTokenUtil;
import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.domain.VerificationToken;
import com.funfic.karpilovich.dto.JwtResponse;
import com.funfic.karpilovich.dto.LoginRequest;
import com.funfic.karpilovich.dto.RegistrationRequest;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.service.MailSenderService;
import com.funfic.karpilovich.service.UserService;
import com.funfic.karpilovich.service.VerificationTokenService;

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
    public ResponseEntity<?> register(@Valid RegistrationRequest registrationRequest,
            HttpServletRequest request) {
            try {
                User user = registerUser(registrationRequest, request);
                return ResponseEntity.ok(user);
            } catch (ServiceException e) {     
        }
        return ResponseEntity.badRequest().body(registrationRequest);
    }

//    @GetMapping("/activation/{token}")
//    public String confirmRegistration(Model model, @PathVariable String token) {
//        String address;
//        try {
//            address = confirmEmail(token);
//        } catch (ServiceException e) {
//            model.addAttribute("error", "Invalid link");
//            address = "regi";
//        } finally {
//            verificationTokenService.deleteToken(token);
//        }
//        return address;
//    }
//    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenUtil.generateToken(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (Exception e) {
            
        }
        return ResponseEntity.badRequest().body(loginRequest);
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
    
    private Authentication authenticate(String username, String password) throws Exception {
        try {
           return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    } 
    

   private User registerUser(RegistrationRequest registrationRequest, HttpServletRequest request) throws ServiceException {
        User user = userService.save(registrationRequest);
        sendTokenToUser(user.getUsername(), request.getRequestURL(), verificationTokenService.createToken(user));
        return user;
    }

    private void sendTokenToUser(String email, StringBuffer url, VerificationToken token) {
        mailSenderService.sendRegistrationConfirmation(email,
                url.append("/activation/").append(token.getToken()).toString());
    }
    
    private String confirmEmail(String token) throws ServiceException {
        userService.confirmRegistration(token);
       return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "registration";
    }
}