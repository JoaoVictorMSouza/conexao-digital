package com.conexao_digital.frontoffice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.conexao_digital.frontoffice.config.mapping.dto.*;
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.addMappings(new ProdutoFrontofficeDTOMap());

        modelMapper.addMappings(new ImagemProdutoFrontofficeDTOMap());

        return modelMapper;
    }
}