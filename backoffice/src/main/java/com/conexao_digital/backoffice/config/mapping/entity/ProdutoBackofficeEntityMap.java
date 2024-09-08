package com.conexao_digital.backoffice.config.mapping.entity;

import org.modelmapper.PropertyMap;

import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;

public class ProdutoBackofficeEntityMap extends PropertyMap<ProdutoBackofficeDTO, ProdutoBackofficeEntity> {
    @Override
    protected void configure() {
        map().setDsNome(source.getNome());
        map().setVlAvaliacao(source.getAvaliacao());
        map().setDsDetalhe(source.getDescricaoDetalhada());
        map().setVlPreco(source.getPreco());
        map().setQtdEstoque(source.getQuantidadeEstoque());
        map().setAtivo(source.isAtivo());
    }
}