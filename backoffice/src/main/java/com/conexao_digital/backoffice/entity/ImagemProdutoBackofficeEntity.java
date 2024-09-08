package com.conexao_digital.backoffice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "imagem_produto_backoffice")
public class ImagemProdutoBackofficeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagem;
    private boolean imagemPrincipal;
    private String nomeArquivo;
    private String caminho;
    @ManyToOne
    @JoinColumn(name = "fk_produto")
    private ProdutoBackofficeEntity produto;

    public Integer getIdImagem() {
        return idImagem;
    }

    public boolean isImagemPrincipal() {
        return imagemPrincipal;
    }
    public void setImagemPrincipal(boolean imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminho() {
        return caminho;
    }
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public ProdutoBackofficeEntity getProduto() {
        return produto;
    }
    public void setProduto(ProdutoBackofficeEntity produto) {
        this.produto = produto;
    } 
}
