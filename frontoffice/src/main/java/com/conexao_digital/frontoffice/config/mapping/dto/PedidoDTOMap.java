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
        map().setIdStatusPagamento(source.getIdStatusPagamento());
        map().setValorTotal(source.getVlTotal());
    }
}