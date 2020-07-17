package com.funfic.karpilovich.repository.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Genre;

@Projection(name="genreProjection", types= {Genre.class})
public interface GenreProjection {
    
    String getName();
}