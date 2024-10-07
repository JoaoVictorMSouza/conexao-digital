package com.conexao_digital.frontoffice.dto;

import com.conexao_digital.frontoffice.enums.EnderecoTipoEnum;

public class EnderecoDTO {
    private int idEndereco;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private boolean padrao;
    private EnderecoTipoEnum tipoEndereco;
    private int idTipoEndereco;

    public int getIdEndereco() {
        return idEndereco;
    }
    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }
    
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }

    public boolean isPadrao() {
        return padrao;
    }
    public void setPadrao(boolean padrao) {
        this.padrao = padrao;
    }

    public EnderecoTipoEnum getTipoEndereco() {
        return tipoEndereco;
    }
    private void setTipoEnderecoById(int id) {
        this.tipoEndereco = EnderecoTipoEnum.fromId(this.idTipoEndereco);;
    }
    public void setTipoEndereco() {
        this.tipoEndereco = EnderecoTipoEnum.fromId(this.idTipoEndereco);
    }

    public int getIdTipoEndereco() {
        return idTipoEndereco;
    }
    public void setIdTipoEndereco(int idTipoEndereco) {
        this.idTipoEndereco = idTipoEndereco;
        this.setTipoEnderecoById(idTipoEndereco);
    }
}
