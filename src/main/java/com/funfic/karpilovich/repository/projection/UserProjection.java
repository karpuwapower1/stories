package com.funfic.karpilovich.repository.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.User;

@Projection(name = "bookWithoutContext", types = { User.class })
public interface UserProjection {

    Long getId();

    String getFirstName();

    String getLastName();
}