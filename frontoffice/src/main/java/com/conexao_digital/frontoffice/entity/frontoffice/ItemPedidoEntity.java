package com.conexao_digital.frontoffice.entity.frontoffice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido_item_frontoffice")
public class ItemPedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemPedido;
    private int nrQuantidade;
    private double vlPrecoUnitario;
    private int idProduto;
    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private PedidoEntity pedido;

    public Long getIdItemPedido() {
        return idItemPedido;
    }
    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public int getNrQuantidade() {
        return nrQuantidade;
    }
    public void setNrQuantidade(int nrQuantidade) {
        this.nrQuantidade = nrQuantidade;
    }

    public double getVlPrecoUnitario() {
        return vlPrecoUnitario;
    }
    public void setVlPrecoUnitario(double vlPrecoUnitario) {
        this.vlPrecoUnitario = vlPrecoUnitario;
    }

    public int getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public PedidoEntity getPedido() {
        return pedido;
    }
    public void setPedido(PedidoEntity pedido) {
        this.pedido = pedido;
    }    
}
