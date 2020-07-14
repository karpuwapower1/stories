package com.funfic.karpilovich.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @NonNull
    private String jwttoken;
}