package com.conexao_digital.backoffice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.conexao_digital.backoffice.config.mapping.entity.*;
import com.conexao_digital.backoffice.config.mapping.dto.*;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new UsuarioBackofficeEntityMap());
        modelMapper.addMappings(new UsuarioBackofficeDTOMap());

        return modelMapper;
    }
}