package com.conexao_digital.backoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;
import com.conexao_digital.backoffice.dto.UsuarioBackofficeDTO;
import com.conexao_digital.backoffice.entity.UsuarioBackofficeEntity;

public class UsuarioBackofficeDTOMap extends PropertyMap<UsuarioBackofficeEntity, UsuarioBackofficeDTO> {
    @Override
    protected void configure() {
        map().setNome(source.getDsNome());
        map().setCpf(source.getDsCpf());
        map().setEmail(source.getDsEmail());
        map().setAtivo(source.isAtivo());
        map().setIdUsuarioGrupo(source.getIdGrupo());
        // map().setUsuarioGrupo();
        map().setSenha("");
        map().setConfirmacaoSenha("");
    }
}