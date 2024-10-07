package com.conexao_digital.frontoffice.dto;

import org.springframework.security.core.userdetails.UserDetails;

import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;

public class UsuarioLogadoDTO {
    private int id;
    private String email;
    private String nome;
    public UsuarioLogadoDTO(UserDetails usuario, UsuarioFrontofficeEntity usuarioFrontoffice) {
        this.email = usuario.getUsername();
        if (usuarioFrontoffice.getDsNome().indexOf(" ") > 0) {
            this.nome = usuarioFrontoffice.getDsNome().substring(0, usuarioFrontoffice.getDsNome().indexOf(" "));
        } else {
            this.nome = usuarioFrontoffice.getDsNome();
        }
        this.id = usuarioFrontoffice.getIdUsuario();
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
}
