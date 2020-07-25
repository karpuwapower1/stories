package com.funfic.karpilovich.dto.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Chapter;

@Projection(name = "chapterProjection", types = { Chapter.class })
public interface ChapterProjection {

    Long getId();

    String getName();

    short getNumber();

    String getText();
}