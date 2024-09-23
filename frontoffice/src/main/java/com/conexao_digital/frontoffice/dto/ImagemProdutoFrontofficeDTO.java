package com.conexao_digital.frontoffice.dto;

public class ImagemProdutoFrontofficeDTO {
    private int id;
    private String nomeArquivo;
    private String caminho;
    private ProdutoFrontofficeDTO produto;
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

    public ProdutoFrontofficeDTO getProduto() {
        return produto;
    }
    public void setProduto(ProdutoFrontofficeDTO produto) {
        this.produto = produto;
    }

    public boolean isImagemPrincipal() {
        return imagemPrincipal;
    }
    public void setImagemPrincipal(boolean imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }
}
