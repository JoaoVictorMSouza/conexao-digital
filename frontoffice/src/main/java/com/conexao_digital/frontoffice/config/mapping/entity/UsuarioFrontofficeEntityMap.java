package com.conexao_digital.frontoffice.config.mapping.entity;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.UsuarioFrontofficeDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;

public class UsuarioFrontofficeEntityMap extends PropertyMap<UsuarioFrontofficeDTO, UsuarioFrontofficeEntity> {
    @Override
    protected void configure() {
        map().setDsNome(source.getNome());
        map().setDsCpf(source.getCpf());
        map().setDsEmail(source.getEmail());
        map().setDsSenha(source.getSenha());
        map().setDtNascimento(source.getDataNascimento());
        map().setIdGenero(source.getIdGeneroUsuario());
    }
}