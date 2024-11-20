package com.conexao_digital.frontoffice.config.mapping.entity;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.ItemCarrinhoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.ItemPedidoEntity;

public class ItemPedidoEntityMap extends PropertyMap<ItemCarrinhoDTO, ItemPedidoEntity> {
    @Override
    protected void configure() {
        map().setNrQuantidade(source.getQuantidade());
        map().setVlPrecoUnitario(source.getProduto().getPreco());
    }
}