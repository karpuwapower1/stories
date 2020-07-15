package com.funfic.karpilovich.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funfic.karpilovich.domain.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Long>{

}
