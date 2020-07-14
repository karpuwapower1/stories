package com.funfic.karpilovich.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.User;

@Projection(name = "bookWithoutContext", types = { Book.class, User.class })
public interface BookWithoutContextProjection {
    
    Long getId();
    String getName();
    String getDescription();
    Long getUserId();
    String getUserFirstName();
    String getUserLastName();

}