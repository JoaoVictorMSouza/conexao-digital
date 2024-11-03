package com.conexao_digital.frontoffice.entity.frontoffice;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido_frontoffice")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    private Date dhPedido;
    private double vlSubtotal;
    private double vlFrete;
    private double vlTotal;
    private int idStatusPagamento;
    private int idFormaPagamento;
    private int idEnderecoEntrega;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedidoEntity> itens;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private UsuarioFrontofficeEntity usuario;

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

    public int getIdStatusPagamento() {
        return idStatusPagamento;
    }
    public void setIdStatusPagamento(int idStatusPagamento) {
        this.idStatusPagamento = idStatusPagamento;
    }

    public int getIdFormaPagamento() {
        return idFormaPagamento;
    }
    public void setIdFormaPagamento(int idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public int getIdEnderecoEntrega() {
        return idEnderecoEntrega;
    }
    public void setIdEnderecoEntrega(int idEnderecoEntrega) {
        this.idEnderecoEntrega = idEnderecoEntrega;
    }

    public List<ItemPedidoEntity> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedidoEntity> itens) {
        this.itens = itens;
    }

    public UsuarioFrontofficeEntity getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioFrontofficeEntity usuario) {
        this.usuario = usuario;
    }
}
