package com.funfic.karpilovich.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
}