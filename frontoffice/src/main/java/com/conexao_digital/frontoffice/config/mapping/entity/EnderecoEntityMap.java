package com.conexao_digital.frontoffice.config.mapping.entity;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.EnderecoEntity;

public class EnderecoEntityMap extends PropertyMap<EnderecoDTO, EnderecoEntity> {
    @Override
    protected void configure() {
        map().setDsCep(source.getCep());
        map().setDsLogradouro(source.getLogradouro());
        map().setDsNumero(source.getNumero());
        map().setDsComplemento(source.getComplemento());
        map().setDsBairro(source.getBairro());
        map().setDsCidade(source.getCidade());
        map().setDsUf(source.getUf());
        map().setPadrao(source.isPadrao());
        map().setIdTipoEndereco(source.getIdTipoEndereco());
    }
}