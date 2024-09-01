package com.conexao_digital.backoffice.dto;

import com.conexao_digital.backoffice.enums.UsuarioGrupo;

public class UsuarioBackofficeDTO {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String confirmacaoSenha;
    private int idUsuarioGrupo;
    private UsuarioGrupo usuarioGrupo;
    private boolean ativo;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }
    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public int getIdUsuarioGrupo() {
        return idUsuarioGrupo;
    }

    public void setIdUsuarioGrupo(int idUsuarioGrupo) {
        this.idUsuarioGrupo = idUsuarioGrupo;
        setUsuarioGrupoById(idUsuarioGrupo);
    }

    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public UsuarioGrupo getUsuarioGrupo() {
        return usuarioGrupo;
    }

    private void setUsuarioGrupoById(int id) {
        this.usuarioGrupo = UsuarioGrupo.fromId(id);
    }

    public void setUsuarioGrupo() {
        this.usuarioGrupo = UsuarioGrupo.fromId(this.idUsuarioGrupo);
    }
}
