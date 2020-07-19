package com.funfic.karpilovich.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.repository.TagRepository;
import com.funfic.karpilovich.repository.TagRepository.TagQuantity;
import com.funfic.karpilovich.service.TagService;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<TagQuantity> findPopularTag() {
        return tagRepository.findFits100TagQuantityByNameNative();
    }
}