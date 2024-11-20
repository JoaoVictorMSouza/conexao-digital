package com.conexao_digital.frontoffice.dto;

public class ItemPedidoDTO {
    private Long idItemPedido;
    private int quantidade;
    private double precoUnitario;
    private int idProduto;

    private ProdutoFrontofficeDTO produto;

    public Long getIdItemPedido() {
        return idItemPedido;
    }
    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public ProdutoFrontofficeDTO getProduto() {
        return produto;
    }
    public void setProduto(ProdutoFrontofficeDTO produto) {
        this.produto = produto;
    }
}
