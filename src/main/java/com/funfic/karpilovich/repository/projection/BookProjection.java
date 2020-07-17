package com.funfic.karpilovich.repository.projection;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Book;

@Projection(name = "bookProjection", types = { Book.class })
public interface BookProjection {

    List<ChapterProjection> getChapters();

    UserProjection getUser();

    List<CommentProjection> getComments();
}