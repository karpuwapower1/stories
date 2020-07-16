package com.funfic.karpilovich.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.funfic.karpilovich.domain.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}