package com.funfic.karpilovich.controller.util;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.dto.BookWithoutContextDto;
import com.funfic.karpilovich.repository.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.repository.projection.GenreProjection;
import com.funfic.karpilovich.repository.projection.TagProjection;
import com.funfic.karpilovich.repository.projection.UserProjection;

@Component
public class BookProjectionMapper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserProjectionResponseAssembler<UserProjection> userAssembler;
    @Autowired
    private GenreProjectionResponseAssembler<GenreProjection> genreAssembler;
    @Autowired
    private TagProjectionResponseAssembler<TagProjection> tagAssembler;

    public BookWithoutContextDto mapToBookWithoutContextDto(BookWithoutContextProjection projection) {
        return projection == null ? new BookWithoutContextDto()
                : modelMapper.map(projection, BookWithoutContextDto.class);
    }

    @PostConstruct
    public void setup() {
        modelMapper.createTypeMap(BookWithoutContextProjection.class, BookWithoutContextDto.class).addMappings(m -> {
            m.skip(BookWithoutContextDto::setUser);
            m.skip(BookWithoutContextDto::setGenres);
            m.skip(BookWithoutContextDto::setTags);
        }).setPostConverter(toBookWithoutContextDtoConverter());
    }

    private Converter<BookWithoutContextProjection, BookWithoutContextDto> toBookWithoutContextDtoConverter() {
        return context -> {
            BookWithoutContextProjection source = context.getSource();
            BookWithoutContextDto destination = context.getDestination();
            destination.setUser(userAssembler.toModel(source.getUser()));
            destination.setGenres(genreAssembler.toCollectionModel(source.getGenres()));
            destination.setTags(tagAssembler.toCollectionModel(source.getTags()));
            return destination;
        };
    }
}