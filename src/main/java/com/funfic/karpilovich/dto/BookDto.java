package com.funfic.karpilovich.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    
    private Long id;
    private String name;
    private String description;
    private Long userId;

}
