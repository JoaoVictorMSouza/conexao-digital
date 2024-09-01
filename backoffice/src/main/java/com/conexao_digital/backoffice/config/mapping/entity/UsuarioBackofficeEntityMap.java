package com.conexao_digital.backoffice.config.mapping.entity;

import org.modelmapper.PropertyMap;
import com.conexao_digital.backoffice.dto.UsuarioBackofficeDTO;
import com.conexao_digital.backoffice.entity.UsuarioBackofficeEntity;

public class UsuarioBackofficeEntityMap extends PropertyMap<UsuarioBackofficeDTO, UsuarioBackofficeEntity> {
    @Override
    protected void configure() {
        map().setDsNome(source.getNome());
        map().setDsCpf(source.getCpf());
        map().setDsEmail(source.getEmail());
        map().setDsSenha(source.getSenha());
        map().setAtivo(source.isAtivo());
        map().setIdGrupo(source.getIdUsuarioGrupo());
    }
}