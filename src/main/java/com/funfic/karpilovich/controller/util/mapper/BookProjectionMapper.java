package com.funfic.karpilovich.controller.util.mapper;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funfic.karpilovich.controller.util.assembler.GenreProjectionResponseAssembler;
import com.funfic.karpilovich.controller.util.assembler.TagProjectionResponseAssembler;
import com.funfic.karpilovich.controller.util.assembler.UserProjectionResponseAssembler;
import com.funfic.karpilovich.dto.projection.BookWithoutContextProjection;
import com.funfic.karpilovich.dto.projection.GenreProjection;
import com.funfic.karpilovich.dto.projection.TagProjection;
import com.funfic.karpilovich.dto.projection.UserProjection;
import com.funfic.karpilovich.dto.response.BookWithoutContextResponse;

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

    public BookWithoutContextResponse mapToBookWithoutContextDto(BookWithoutContextProjection projection) {
        return projection == null ? new BookWithoutContextResponse()
                : modelMapper.map(projection, BookWithoutContextResponse.class);
    }

    @PostConstruct
    public void setup() {
        modelMapper.createTypeMap(BookWithoutContextProjection.class, BookWithoutContextResponse.class).addMappings(m -> {
            m.skip(BookWithoutContextResponse::setUser);
            m.skip(BookWithoutContextResponse::setGenres);
            m.skip(BookWithoutContextResponse::setTags);
        }).setPostConverter(toBookWithoutContextDtoConverter());
    }

    private Converter<BookWithoutContextProjection, BookWithoutContextResponse> toBookWithoutContextDtoConverter() {
        return context -> {
            BookWithoutContextProjection source = context.getSource();
            BookWithoutContextResponse destination = context.getDestination();
            setParamentersToDestination(source, destination);
            return destination;
        };
    }

    private void setParamentersToDestination(BookWithoutContextProjection source, BookWithoutContextResponse destination) {
        destination.setUser(userAssembler.toModel(source.getUser()));
        destination.setGenres(genreAssembler.toCollectionModel(source.getGenres()));
        destination.setTags(tagAssembler.toCollectionModel(source.getTags()));
    }
}