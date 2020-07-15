package com.funfic.karpilovich.service;

import java.util.List;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.Chapter;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.exception.ServiceException;

public interface ChapterService {
    
    List<Chapter> addAll(BookRequest bookRequest, Book book) throws ServiceException;

}
