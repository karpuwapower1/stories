package com.funfic.karpilovich.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfic.karpilovich.repository.GenreRepository;
import com.funfic.karpilovich.repository.GenreRepository.GenreQuantity;
import com.funfic.karpilovich.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<GenreQuantity> getAllByPopularity() {
        return genreRepository.findTagQuantityByNameNative();
    }
}