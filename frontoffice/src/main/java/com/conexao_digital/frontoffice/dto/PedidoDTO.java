package com.conexao_digital.frontoffice.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.conexao_digital.frontoffice.enums.StatusPedidoEnum;

public class PedidoDTO {
    private long idPedido;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dataPedidoFront;
    private double valorTotal;
    private double valorFrete;
    private double valorSubtotal;
    private StatusPedidoEnum statusPedidoEnum;
    private String dsStatusPedidoEnum;
    private int idStatusPedidoEnum;
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

    public double getValorSubtotal() {
        return valorSubtotal;
    }
    public void setValorSubtotal(double valorSubtotal) {
        this.valorSubtotal = valorSubtotal;
    }

    public StatusPedidoEnum getStatusPedidoEnum() {
        return statusPedidoEnum;
    }
    public void setStatusPedidoEnum(StatusPedidoEnum statusPedidoEnum) {
        this.statusPedidoEnum = statusPedidoEnum;
        this.idStatusPedidoEnum = statusPedidoEnum.getId();
    }

    public String getDsStatusPedido() {
        return dsStatusPedidoEnum;
    }
    public void setDsStatusPedido(String dsStatusPedido) {
        this.dsStatusPedidoEnum = dsStatusPedido;
    }

    public int getIdStatusPedido() {
        return idStatusPedidoEnum;
    }
    public void setIdStatusPedido(int idStatusPedido) {
        this.idStatusPedidoEnum = idStatusPedido;
        this.statusPedidoEnum = StatusPedidoEnum.fromId(idStatusPedido);
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
