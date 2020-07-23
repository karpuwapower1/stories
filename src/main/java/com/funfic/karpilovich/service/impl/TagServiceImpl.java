package com.funfic.karpilovich.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.domain.Tag;
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

    @Override
    public Set<Tag> takeCorrespondTagsFromStorage(Set<Tag> tags) {
        return (tags == null || tags.isEmpty()) ? tags
                : tags.stream().map(tag -> takeCorrespondTagFromDb(tag)).collect(Collectors.toSet());
    }

    private Tag takeCorrespondTagFromDb(Tag tag) {
        Tag tagFromDB = getTagFromDb(tag);
        tagFromDB.setBooks(tag.getBooks());
        return tagFromDB;
    }

    private Tag getTagFromDb(Tag tag) {
        return tagRepository.existsByName(tag.getName()) ? tagRepository.findByName(tag.getName()) : tag;
    }
}