package com.conexao_digital.frontoffice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.conexao_digital.frontoffice.config.mapping.dto.*;
import com.conexao_digital.frontoffice.config.mapping.entity.*;
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.addMappings(new ProdutoFrontofficeDTOMap());

        modelMapper.addMappings(new ImagemProdutoFrontofficeDTOMap());

        modelMapper.addMappings(new UsuarioFrontofficeDTOMap());
        modelMapper.addMappings(new UsuarioFrontofficeEntityMap());

        modelMapper.addMappings(new EnderecoUsuarioFrontofficeDTOMap());
        modelMapper.addMappings(new EnderecoUsuarioFrontofficeEntityMap());

        return modelMapper;
    }
}