package com.conexao_digital.frontoffice.dto;

public class ItemCarrinhoDTO {
    private ProdutoFrontofficeDTO produto;
    private int quantidade;

    public ItemCarrinhoDTO() {
    }

    public ItemCarrinhoDTO(ProdutoFrontofficeDTO produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ProdutoFrontofficeDTO getProduto() {
        return produto;
    }
    public void setProduto(ProdutoFrontofficeDTO produto) {
        this.produto = produto;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
