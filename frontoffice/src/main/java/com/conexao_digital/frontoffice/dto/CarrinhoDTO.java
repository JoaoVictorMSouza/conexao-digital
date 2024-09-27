package com.conexao_digital.frontoffice.dto;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDTO {
    private List<ItemCarrinhoDTO> itens = new ArrayList<>();
    private int quantidadeItens;
    private double valorTotalItens;
    private double valorTotal;
    private double valorFrete;

    public List<ItemCarrinhoDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemCarrinhoDTO> itens) {
        this.itens = itens;
    }

    public int getQuantidadeItens() {
        return quantidadeItens;
    }
    public void setQuantidadeItens(int quantidadeItens) {
        this.quantidadeItens = quantidadeItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorFrete() {
        return valorFrete;
    }
    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public double getValorTotalItens() {
        return valorTotalItens;
    }
    public void setValorTotalItens(double valorTotalItens) {
        this.valorTotalItens = valorTotalItens;
    }
}
