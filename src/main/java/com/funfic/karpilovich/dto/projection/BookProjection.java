package com.funfic.karpilovich.dto.projection;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Book;

@Projection(name = "bookProjection", types = { Book.class })
public interface BookProjection extends BookWithoutContextProjection {

    List<ChapterProjection> getChapters();
}