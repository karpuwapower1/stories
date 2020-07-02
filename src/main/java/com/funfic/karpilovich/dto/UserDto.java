package com.funfic.karpilovich.dto;

import java.util.Set;

import org.springframework.lang.NonNull;

import com.funfic.karpilovich.domain.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @NonNull
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Set<Role> roles;
    private Set<BookDto> books;
}