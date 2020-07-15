package com.funfic.karpilovich.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.Chapter;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.exception.ServiceException;
import com.funfic.karpilovich.service.ChapterService;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Override
    public List<Chapter> addAll(BookRequest bookRequest, Book book) throws ServiceException {
        return null;
    }

}