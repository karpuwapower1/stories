package com.funfic.karpilovich.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    
    @NotBlank
    private String book;
    @NotEmpty
    private List<String> chapters;
    private List<String> genres;
    private List<String> tags;
}