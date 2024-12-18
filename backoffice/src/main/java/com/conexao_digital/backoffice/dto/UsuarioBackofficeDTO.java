package com.conexao_digital.backoffice.dto;

import com.conexao_digital.backoffice.enums.UsuarioGrupoEnum;

public class UsuarioBackofficeDTO {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String confirmacaoSenha;
    private int idUsuarioGrupo;
    private UsuarioGrupoEnum usuarioGrupo;
    private boolean ativo;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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

    public UsuarioGrupoEnum getUsuarioGrupo() {
        return usuarioGrupo;
    }

    private void setUsuarioGrupoById(int id) {
        this.usuarioGrupo = UsuarioGrupoEnum.fromId(id);
    }

    public void setUsuarioGrupo() {
        this.usuarioGrupo = UsuarioGrupoEnum.fromId(this.idUsuarioGrupo);
    }
}
