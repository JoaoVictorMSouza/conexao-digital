package com.conexao_digital.frontoffice.config.mapping.entity;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.EnderecoEntity;

public class EnderecoUsuarioFrontofficeEntityMap extends PropertyMap<EnderecoDTO, EnderecoEntity> {
    @Override
    protected void configure() {
        map().setIdEndereco(source.getIdEndereco());
        map().setDsCep(source.getCep());
        map().setDsLogradouro(source.getLogradouro());
        map().setDsNumero(source.getNumero());
        map().setDsComplemento(source.getComplemento());
        map().setDsBairro(source.getBairro());
        map().setDsCidade(source.getCidade());
        map().setDsUf(source.getUf());
    }
}