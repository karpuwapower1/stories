package com.funfic.karpilovich.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String name;
    private String description;
    private Long userId;
    private List<ChapterDto> chapters;
}