package com.conexao_digital.backoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.backoffice.dto.PedidoFrontofficeDTO;
import com.conexao_digital.backoffice.entity.PedidoFrontofficeEntity;

public class PedidoFrontofficeDTOMap extends PropertyMap<PedidoFrontofficeEntity, PedidoFrontofficeDTO> {
    @Override
    protected void configure() {
        map().setDataPedidoFront(source.getDhPedido());
        map().setIdPedido(source.getIdPedido());
        // map().setStatusPagamento(StatusPagamentoEnum.fromId(source.getIdStatusPagamento()));
        map().setIdStatusPedidoEnum(source.getIdStatusPedido());
        map().setValorTotal(source.getVlTotal());
        map().setValorFrete(source.getVlFrete());
    }
}