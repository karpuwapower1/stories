package com.funfic.karpilovich.repository.projection;

import org.springframework.core.annotation.Order;
import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Chapter;

@Projection(name = "chapterProjection", types = { Chapter.class })
public interface ChapterProjection {

    String getName();

    @Order
    short getNumber();

    String getText();
}