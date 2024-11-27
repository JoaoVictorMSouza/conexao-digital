package com.conexao_digital.frontoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.PedidoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.PedidoEntity;

public class PedidoDTOMap extends PropertyMap<PedidoEntity, PedidoDTO> {
    @Override
    protected void configure() {
        map().setDataPedidoFront(source.getDhPedido());
        map().setIdPedido(source.getIdPedido());
        // map().setStatusPagamento(StatusPagamentoEnum.fromId(source.getIdStatusPagamento()));
        map().setIdStatusPedido(source.getIdStatusPedido());
        map().setValorTotal(source.getVlTotal());
        map().setValorFrete(source.getVlFrete());
        map().setValorSubtotal(source.getVlSubtotal());
        map().setIdFormaPagamento(source.getIdFormaPagamento());
    }
}