package com.funfic.karpilovich.dto.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Genre;

@Projection(name = "genreProjection", types = { Genre.class })
public interface GenreProjection {
    
    Integer getId();

    String getName();
}