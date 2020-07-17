package com.funfic.karpilovich.repository.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Tag;

@Projection(name = "tagProjection", types = { Tag.class })
public interface TagProjection {

    String getName();
}