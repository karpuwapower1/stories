package com.funfic.karpilovich.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfic.karpilovich.controller.util.assembler.MainPageResponseAssembler;
import com.funfic.karpilovich.controller.util.mapper.MainPageDtoMapper;
import com.funfic.karpilovich.dto.projection.UserProjection;
import com.funfic.karpilovich.dto.response.MainPageResponse;
import com.funfic.karpilovich.repository.GenreRepository.GenreQuantity;
import com.funfic.karpilovich.repository.TagRepository.TagQuantity;
import com.funfic.karpilovich.service.GenreService;
import com.funfic.karpilovich.service.TagService;
import com.funfic.karpilovich.service.UserService;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    private TagService tagService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private UserService userService;
    @Autowired
    private MainPageDtoMapper mainPageMapper;
    @Autowired
    private MainPageResponseAssembler<MainPageResponse> mainPageResponseAssembler;

    @GetMapping
    public ResponseEntity<?> main() {
        EntityModel<MainPageResponse> response = createResponse();
        return ResponseEntity.ok(response);
    }

    private EntityModel<MainPageResponse> createResponse() {
        MainPageResponse dto = createMainPageDto();
        return mainPageResponseAssembler.toModel(dto);
    }

    private MainPageResponse createMainPageDto() {
        return mainPageMapper.mapToMainPageDto(getCurrentUser(), findTags(), findGenres());
    }

    private UserProjection getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : userService.getByUsername(authentication.getName());
    }

    private List<TagQuantity> findTags() {
        return tagService.findPopularTag();
    }

    private List<GenreQuantity> findGenres() {
        return genreService.getAllByPopularity();
    }
}