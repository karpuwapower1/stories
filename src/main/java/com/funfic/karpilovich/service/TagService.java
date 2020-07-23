package com.funfic.karpilovich.service;

import java.util.List;
import java.util.Set;

import com.funfic.karpilovich.domain.Tag;
import com.funfic.karpilovich.repository.TagRepository.TagQuantity;

public interface TagService {
    
    List<TagQuantity> findPopularTag();
    
    Set<Tag> takeCorrespondTagsFromStorage(Set<Tag> tags);
}