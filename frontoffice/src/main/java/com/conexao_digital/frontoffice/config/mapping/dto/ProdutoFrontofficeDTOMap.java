package com.conexao_digital.frontoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.ProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.entity.backoffice.ProdutoBackofficeEntity;

public class ProdutoFrontofficeDTOMap extends PropertyMap<ProdutoBackofficeEntity, ProdutoFrontofficeDTO> {
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