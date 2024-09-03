package com.conexao_digital.backoffice.dto;

import org.springframework.security.core.userdetails.UserDetails;
import com.conexao_digital.backoffice.entity.UsuarioBackofficeEntity;
import com.conexao_digital.backoffice.enums.UsuarioGrupo;
import com.conexao_digital.backoffice.utils.AutenticacaoUtils;

public class UsuarioLogadoDTO {
    private int id;
    private String email;
    private String grupo;
    private String nome;
    private UsuarioGrupo usuarioGrupo;;

    public UsuarioLogadoDTO(UserDetails usuario, UsuarioBackofficeEntity usuarioBackoffice) {
        this.email = usuario.getUsername();
        this.usuarioGrupo = AutenticacaoUtils.retornarUsuarioGrupo(usuario);
        this.grupo = this.usuarioGrupo.name();
        this.nome = usuarioBackoffice.getDsNome();
        this.id = usuarioBackoffice.getIdUsuario();
    }

    public String getEmail() {
        return email;
    }

    public String getGrupo() {
        return grupo;
    }

    public UsuarioGrupo getUsuarioGrupo() {
        return usuarioGrupo;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
}
