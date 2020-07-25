package com.funfic.karpilovich.dto.projection;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Role;
import com.funfic.karpilovich.domain.User;

@Projection(name = "bookWithoutContext", types = { User.class })
public interface UserProjection {

    Long getId();

    String getFirstName();

    String getLastName();
    
    Set<Role> getRoles();
    
    Boolean getEnabled();
}