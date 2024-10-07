package com.conexao_digital.frontoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.UsuarioFrontofficeDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;

public class UsuarioFrontofficeDTOMap extends PropertyMap<UsuarioFrontofficeEntity, UsuarioFrontofficeDTO> {
    @Override
    protected void configure() {
        map().setId(source.getIdUsuario().intValue());
        map().setNome(source.getDsNome());
        map().setCpf(source.getDsCpf());
        map().setEmail(source.getDsEmail());
        map().setDataNascimento(source.getDtNascimento());
        map().setIdGeneroUsuario(source.getIdGenero());
        map().setSenha("");
        map().setConfirmacaoSenha("");
    }
}