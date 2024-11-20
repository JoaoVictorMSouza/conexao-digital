package com.conexao_digital.frontoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.ItemPedidoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.ItemPedidoEntity;

public class ItemPedidoDTOMap extends PropertyMap<ItemPedidoEntity, ItemPedidoDTO> {
    @Override
    protected void configure() {
        map().setIdItemPedido(source.getIdItemPedido());
        map().setQuantidade(source.getNrQuantidade());
        map().setPrecoUnitario(source.getVlPrecoUnitario());
    }
}