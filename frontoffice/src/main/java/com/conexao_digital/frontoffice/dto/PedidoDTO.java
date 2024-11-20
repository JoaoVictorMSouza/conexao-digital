package com.conexao_digital.frontoffice.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.conexao_digital.frontoffice.enums.StatusPagamentoEnum;

public class PedidoDTO {
    private long idPedido;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dataPedidoFront;
    private double valorTotal;
    private double valorFrete;
    private StatusPagamentoEnum statusPagamento;
    private String dsStatusPagamento;
    private int idStatusPagamento;
    private int idFormaPagamento;

    private List<ItemPedidoDTO> itensPedido;
    
    private EnderecoDTO enderecoEntrega;

    public long getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public Date getDataPedidoFront() {
        return dataPedidoFront;
    }
    public void setDataPedidoFront(Date dataPedidoFront) {
        this.dataPedidoFront = dataPedidoFront;
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

    public StatusPagamentoEnum getStatusPagamento() {
        return statusPagamento;
    }
    public void setStatusPagamento(StatusPagamentoEnum statusPagamento) {
        this.statusPagamento = statusPagamento;
        this.idStatusPagamento = statusPagamento.getId();
    }

    public String getDsStatusPagamento() {
        return dsStatusPagamento;
    }
    public void setDsStatusPagamento(String dsStatusPagamento) {
        this.dsStatusPagamento = dsStatusPagamento;
    }

    public int getIdStatusPagamento() {
        return idStatusPagamento;
    }
    public void setIdStatusPagamento(int idStatusPagamento) {
        this.idStatusPagamento = idStatusPagamento;
        this.statusPagamento = StatusPagamentoEnum.fromId(idStatusPagamento);
    }

    public int getIdFormaPagamento() {
        return idFormaPagamento;
    }
    public void setIdFormaPagamento(int idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public List<ItemPedidoDTO> getItensPedido() {
        return itensPedido;
    }
    public void setItensPedido(List<ItemPedidoDTO> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public EnderecoDTO getEnderecoEntrega() {
        return enderecoEntrega;
    }
    public void setEnderecoEntrega(EnderecoDTO enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
}
