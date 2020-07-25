package com.funfic.karpilovich.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    
    @NotBlank
    @Size(min = 3)
    private String firstName;
    @NotBlank
    @Size(min = 3)
    private String lastName;
    @Email
    private String username;
    @NotBlank
    @Size(min = 5)
    private String password;
    @NotBlank
    @Size(min = 5)
    private String confirmPassword;

}
