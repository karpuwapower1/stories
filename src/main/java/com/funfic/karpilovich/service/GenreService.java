package com.funfic.karpilovich.service;

import java.util.List;

import com.funfic.karpilovich.repository.GenreRepository.GenreQuantity;

public interface GenreService {
    
    List<GenreQuantity> getAllByPopularity();
}