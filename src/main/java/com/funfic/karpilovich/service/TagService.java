package com.funfic.karpilovich.service;

import java.util.List;

import com.funfic.karpilovich.repository.TagRepository.TagQuantity;

public interface TagService {
    
    List<TagQuantity> findPopularTag();
}