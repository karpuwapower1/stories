package com.funfic.karpilovich.dto.projection;

import org.springframework.data.rest.core.config.Projection;

import com.funfic.karpilovich.domain.Comment;

@Projection(name = "commentProjection", types = Comment.class)
public interface CommentProjection {

    UserProjection getUser();

    String getComment();
}