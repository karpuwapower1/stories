package com.funfic.karpilovich.projection;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Book;

@Projection(name = "bookWithoutContext", types = {Book.class})
public interface BookWithoutContextProjection {
    
    Long getId();
    String getName();
    String getDescription();
    UserProjection getUser();
    Set<GenreProjection> getGenres();
    Set<TagProjection> getTags();
}