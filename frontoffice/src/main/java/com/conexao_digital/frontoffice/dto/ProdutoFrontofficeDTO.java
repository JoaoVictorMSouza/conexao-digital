package com.conexao_digital.frontoffice.dto;

public class ProdutoFrontofficeDTO {
    private int id;
    private String nome;
    private int quantidadeEstoque;
    private double preco;
    private boolean ativo;
    private String descricaoDetalhada;
    private double avaliacao;
    private String nomeImagemPrincipal;
    private String caminhoImagemPrincipal;

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

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }
    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    public double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getNomeImagemPrincipal() {
        return nomeImagemPrincipal;
    }
    public void setNomeImagemPrincipal(String nomeImagemPrincipal) {
        this.nomeImagemPrincipal = nomeImagemPrincipal;
    }

    public String getCaminhoImagemPrincipal() {
        return caminhoImagemPrincipal;
    }
    public void setCaminhoImagemPrincipal(String caminhoImagemPrincipal) {
        this.caminhoImagemPrincipal = caminhoImagemPrincipal;
    }
}
