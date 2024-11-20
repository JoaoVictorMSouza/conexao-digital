package com.conexao_digital.backoffice.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido_frontoffice")
public class PedidoFrontofficeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    private Date dhPedido;
    private double vlSubtotal;
    private double vlFrete;
    private double vlTotal;
    @Column(name = "ID_STATUS_PEDIDO")
    private int idStatusPedido;

    public Long getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Date getDhPedido() {
        return dhPedido;
    }
    public void setDhPedido(Date dhPedido) {
        this.dhPedido = dhPedido;
    }

    public double getVlSubtotal() {
        return vlSubtotal;
    }
    public void setVlSubtotal(double vlSubtotal) {
        this.vlSubtotal = vlSubtotal;
    }

    public double getVlFrete() {
        return vlFrete;
    }
    public void setVlFrete(double vlFrete) {
        this.vlFrete = vlFrete;
    }

    public double getVlTotal() {
        return vlTotal;
    }
    public void setVlTotal(double vlTotal) {
        this.vlTotal = vlTotal;
    }

    public int getIdStatusPedido() {
        return idStatusPedido;
    }
    public void setIdStatusPedido(int idStatusPedido) {
        this.idStatusPedido = idStatusPedido;
    }
}
