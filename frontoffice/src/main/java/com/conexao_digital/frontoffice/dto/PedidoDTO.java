package com.conexao_digital.frontoffice.dto;

import java.util.Date;

import com.conexao_digital.frontoffice.enums.StatusPagamentoEnum;

public class PedidoDTO {
    private long idPedido;
    private Date dhPedido;
    private double valorTotal;
    private StatusPagamentoEnum statusPagamento;
    private int idStatusPagamento;

    public long getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public Date getDhPedido() {
        return dhPedido;
    }
    public void setDhPedido(Date dhPedido) {
        this.dhPedido = dhPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPagamentoEnum getStatusPagamento() {
        return statusPagamento;
    }
    public void setStatusPagamento(StatusPagamentoEnum statusPagamento) {
        this.statusPagamento = statusPagamento;
        this.idStatusPagamento = statusPagamento.getId();
    }

    public int getIdStatusPagamento() {
        return idStatusPagamento;
    }
    public void setIdStatusPagamento(int idStatusPagamento) {
        this.idStatusPagamento = idStatusPagamento;
        this.statusPagamento = StatusPagamentoEnum.fromId(idStatusPagamento);
    }
}
