package com.conexao_digital.backoffice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto_backoffice")
public class ProdutoBackofficeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduto;
    private String dsNome;
    private double vlAvaliacao;
    private String dsDetalhe;
    private double vlPreco;
    private int qtdEstoque;
    private boolean isAtivo;

    public Integer getIdProduto() {
        return idProduto;
    }

    public String getDsNome() {
        return dsNome;
    }
    public void setDsNome(String dsNome) {
        this.dsNome = dsNome;
    }

    public double getVlAvaliacao() {
        return vlAvaliacao;
    }
    public void setVlAvaliacao(double vlAvaliacao) {
        this.vlAvaliacao = vlAvaliacao;
    }

    public String getDsDetalhe() {
        return dsDetalhe;
    }
    public void setDsDetalhe(String dsDetalhe) {
        this.dsDetalhe = dsDetalhe;
    }

    public double getVlPreco() {
        return vlPreco;
    }
    public void setVlPreco(double vlPreco) {
        this.vlPreco = vlPreco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }
    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }
    
    public boolean isAtivo() {
        return isAtivo;
    }
    public void setAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }
}
