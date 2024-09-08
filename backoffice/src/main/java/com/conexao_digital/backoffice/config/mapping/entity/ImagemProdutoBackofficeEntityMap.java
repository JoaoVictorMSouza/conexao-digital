package com.conexao_digital.backoffice.config.mapping.entity;

import org.modelmapper.PropertyMap;

import com.conexao_digital.backoffice.dto.ImagemProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ImagemProdutoBackofficeEntity;

public class ImagemProdutoBackofficeEntityMap extends PropertyMap<ImagemProdutoBackofficeDTO, ImagemProdutoBackofficeEntity> {
    @Override
    protected void configure() {
        map().setImagemPrincipal(source.isImagemPrincipal());
        map().setNomeArquivo(source.getNomeArquivo());
        map().setCaminho(source.getCaminho());
    }
}