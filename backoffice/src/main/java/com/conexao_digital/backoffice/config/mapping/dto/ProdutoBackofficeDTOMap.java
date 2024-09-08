package com.conexao_digital.backoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;

public class ProdutoBackofficeDTOMap extends PropertyMap<ProdutoBackofficeEntity, ProdutoBackofficeDTO> {
    @Override
    protected void configure() {
        map().setId(source.getIdProduto().intValue());
        map().setNome(source.getDsNome());
        map().setQuantidadeEstoque(source.getQtdEstoque());
        map().setPreco(source.getVlPreco());
        map().setAtivo(source.isAtivo());
        map().setDescricaoDetalhada(source.getDsDetalhe());
        map().setAvaliacao(source.getVlAvaliacao());
    }
}