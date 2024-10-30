package com.conexao_digital.frontoffice.dto;

import java.util.ArrayList;
import java.util.List;

import com.conexao_digital.frontoffice.enums.FormaPagamentoEnum;

public class CarrinhoDTO {
    private List<ItemCarrinhoDTO> itens = new ArrayList<>();
    private int quantidadeItens;
    private double valorTotalItens;
    private double valorTotal;
    private double valorFrete;
    private String cep;
    private int idFormaPagamento;
    private FormaPagamentoEnum formaPagamento;
    private EnderecoDTO endereco;

    public CarrinhoDTO() {
        this.cep = "";
    }

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

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public double getValorTotalItens() {
        return valorTotalItens;
    }
    public void setValorTotalItens(double valorTotalItens) {
        this.valorTotalItens = valorTotalItens;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }
    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public FormaPagamentoEnum getFormaPagamento() {
        return formaPagamento;
    }
    private void setFormaPagamentoById(int id) {
        this.formaPagamento = FormaPagamentoEnum.fromId(this.idFormaPagamento);;
    }
    public void setFormaPagamento() {
        this.formaPagamento = FormaPagamentoEnum.fromId(this.idFormaPagamento);
    }

    public int getIdFormaPagamento() {
        return idFormaPagamento;
    }
    public void setIdFormaPagamento(int idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
        this.setFormaPagamentoById(idFormaPagamento);
    }
}
