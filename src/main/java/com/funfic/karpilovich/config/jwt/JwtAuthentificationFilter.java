package com.funfic.karpilovich.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.funfic.karpilovich.domain.User;
import com.funfic.karpilovich.service.UserService;

public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String JWT_TOKEN_START = "Bearer ";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        authentificateUser(token, request);
        chain.doFilter(request, response);
    }
    
    private String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token != null && token.startsWith(JWT_TOKEN_START)) {
            return token.substring(JWT_TOKEN_START.length());
        }
        return "";
    }

    private void authentificateUser(String token, HttpServletRequest request) {
        if (!token.isEmpty() && jwtTokenUtil.validateToken(token)) {
            User user = getUserFromToken(token);
            UsernamePasswordAuthenticationToken authentication = configureAuthentification(user, request);
            setUserAuthentification(authentication);
        }
    }
    
    private User getUserFromToken(String token) {
        String username = jwtTokenUtil.getUsernameFromJWT(token);
        return (User) userService.loadUserByUsername(username);
    }
    
    private UsernamePasswordAuthenticationToken configureAuthentification(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());
       authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
       return authentication;
    }
    
    private void setUserAuthentification(UsernamePasswordAuthenticationToken authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}