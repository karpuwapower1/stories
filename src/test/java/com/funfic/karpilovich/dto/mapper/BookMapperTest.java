package com.funfic.karpilovich.dto.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.funfic.karpilovich.domain.Book;
import com.funfic.karpilovich.domain.Chapter;
import com.funfic.karpilovich.dto.BookRequest;
import com.funfic.karpilovich.dto.ChapterRequest;

@SpringBootTest
public class BookMapperTest {
    
    @Autowired
    private BookMapper bookMapper;
    
    @Test
    public void mapFromBookRequestToBookTest() {
        ChapterRequest chapterRequest1 = new ChapterRequest("chapter1","chapter 1", (short) 1);
        ChapterRequest chapterRequest2 = new ChapterRequest("chapter2","chapter 2", (short) 2);
        BookRequest bookRequest = new BookRequest("book", "description", Arrays.asList(chapterRequest1, chapterRequest2));
        Book book = bookMapper.mapFromBookRequestToBook(bookRequest);
        assertEquals(book.getName(), bookRequest.getName());
        assertEquals(book.getDescription(), bookRequest.getDescription());
        List<Chapter> chapters = book.getChapters();
        checkChapter(chapters.get(0), chapterRequest1);
        checkChapter(chapters.get(1), chapterRequest2);
    }
    
    private void checkChapter(Chapter chapter, ChapterRequest chapterRequest) {
        assertEquals(chapter.getName(), chapterRequest.getName());
        assertEquals(chapter.getText(), chapterRequest.getText());
        assertEquals(chapter.getNumber(), chapterRequest.getNumber());
    }
}