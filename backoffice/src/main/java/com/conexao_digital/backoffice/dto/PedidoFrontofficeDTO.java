package com.conexao_digital.backoffice.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.conexao_digital.backoffice.enums.StatusPedidoEnum;

public class PedidoFrontofficeDTO {
    private long idPedido;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dataPedidoFront;
    private double valorTotal;
    private double valorFrete;
    private StatusPedidoEnum statusPedidoEnum;
    private String dsStatusPedidoEnum;
    private int idStatusPedidoEnum;

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

    public StatusPedidoEnum getStatusPedidoEnum() {
        return statusPedidoEnum;
    }
    public void setStatusPedidoEnum(StatusPedidoEnum statusPedidoEnum) {
        this.statusPedidoEnum = statusPedidoEnum;
        this.idStatusPedidoEnum = statusPedidoEnum.getId();
    }

    public String getDsStatusPedidoEnum() {
        return dsStatusPedidoEnum;
    }
    public void setDsStatusPedidoEnum(String dsstatusPedidoEnum) {
        this.dsStatusPedidoEnum = dsstatusPedidoEnum;
    }

    public int getIdStatusPedidoEnum() {
        return idStatusPedidoEnum;
    }
    public void setIdStatusPedidoEnum(int idStatusPedido) {
        this.idStatusPedidoEnum = idStatusPedido;
        this.statusPedidoEnum = StatusPedidoEnum.fromId(idStatusPedido);
    }
}
