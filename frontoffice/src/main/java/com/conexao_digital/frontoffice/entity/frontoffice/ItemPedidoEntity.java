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
}
