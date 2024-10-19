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
    private int idUsuario;
    private int idEnderecoEntrega;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedidoEntity> itens;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private UsuarioFrontofficeEntity usuario;
}
