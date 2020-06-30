package com.funfic.karpilovich.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureModelMapper(modelMapper);
        return modelMapper;
    }

    private void configureModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                   .setFieldMatchingEnabled(true)
                   .setSkipNullEnabled(true);
    }
}