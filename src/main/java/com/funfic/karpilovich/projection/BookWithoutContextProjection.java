package com.funfic.karpilovich.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Book;

@Projection(name = "bookWithoutContext", types = { Book.class })
public interface BookWithoutContextProjection {
    
    Long getId();
    String getName();
    String getDescription();

}