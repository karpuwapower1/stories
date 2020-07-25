package com.funfic.karpilovich.dto.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Tag;

@Projection(name = "tagProjection", types = { Tag.class })
public interface TagProjection {
    
    Long getId();

    String getName();
}