package com.funfic.karpilovich.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TokenRequest {

    @NotBlank
    private String token;
}