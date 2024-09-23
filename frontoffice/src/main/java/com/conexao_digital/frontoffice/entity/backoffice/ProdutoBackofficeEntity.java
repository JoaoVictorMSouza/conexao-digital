package com.conexao_digital.frontoffice.entity.backoffice;

import java.util.List;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto_backoffice")
public class ProdutoBackofficeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduto;
    @Column(length = 200)
    private String dsNome;
    private double vlAvaliacao;
    @Column(length = 2000)
    private String dsDetalhe;
    private double vlPreco;
    private int qtdEstoque;
    private boolean isAtivo;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagemProdutoBackofficeEntity> imagens;
    private Date dhCadastro;

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

    public List<ImagemProdutoBackofficeEntity> getImagens() {
        return imagens;
    }
    public void setImagens(List<ImagemProdutoBackofficeEntity> imagens) {
        this.imagens = imagens;
    }

    public Date getDhCadastro() {
        return dhCadastro;
    }
    public void setDhCadastro(Date dhCadastro) {
        this.dhCadastro = dhCadastro;
    }
}
