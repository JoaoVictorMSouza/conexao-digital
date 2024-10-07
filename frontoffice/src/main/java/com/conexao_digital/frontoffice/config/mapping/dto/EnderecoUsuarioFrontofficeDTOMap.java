package com.conexao_digital.frontoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.EnderecoEntity;

public class EnderecoUsuarioFrontofficeDTOMap extends PropertyMap<EnderecoEntity, EnderecoDTO> {
    @Override
    protected void configure() {
        map().setCep(source.getDsCep());
        map().setLogradouro(source.getDsLogradouro());
        map().setNumero(source.getDsNumero());
        map().setComplemento(source.getDsComplemento());
        map().setBairro(source.getDsBairro());
        map().setCidade(source.getDsCidade());
        map().setUf(source.getDsUf());
        map().setPadrao(source.isPadrao());
        map().setIdTipoEndereco(source.getIdTipoEndereco());
    }
}