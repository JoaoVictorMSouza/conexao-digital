package com.conexao_digital.backoffice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_backoffice")
public class UsuarioBackofficeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String dsNome;
    private String dsCpf;
    private String dsEmail;
    private String dsSenha;
    private boolean isAtivo;
    private int idGrupo;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getDsNome() {
        return dsNome;
    }
    public void setDsNome(String dsNome) {
        this.dsNome = dsNome;
    }

    public String getDsCpf() {
        return dsCpf;
    }
    public void setDsCpf(String dsCpf) {
        this.dsCpf = dsCpf;
    }

    public String getDsEmail() {
        return dsEmail;
    }
    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getDsSenha() {
        return dsSenha;
    }
    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public boolean isAtivo() {
        return isAtivo;
    }
    public void setAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public int getIdGrupo() {
        return idGrupo;
    }
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }
}
