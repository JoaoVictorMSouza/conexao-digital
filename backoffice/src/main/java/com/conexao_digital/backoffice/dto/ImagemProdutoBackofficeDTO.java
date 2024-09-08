package com.conexao_digital.backoffice.dto;

public class ImagemProdutoBackofficeDTO {
    private int id;
    private String nomeArquivo;
    private String caminho;
    private ProdutoBackofficeDTO produto;
    private boolean imagemPrincipal;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public ProdutoBackofficeDTO getProduto() {
        return produto;
    }
    public void setProduto(ProdutoBackofficeDTO produto) {
        this.produto = produto;
    }

    public boolean isImagemPrincipal() {
        return imagemPrincipal;
    }
    public void setImagemPrincipal(boolean imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }
}
