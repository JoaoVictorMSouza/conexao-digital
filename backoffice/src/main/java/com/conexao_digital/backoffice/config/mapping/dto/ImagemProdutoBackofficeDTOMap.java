package com.conexao_digital.backoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.backoffice.dto.ImagemProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ImagemProdutoBackofficeEntity;

public class ImagemProdutoBackofficeDTOMap extends PropertyMap<ImagemProdutoBackofficeEntity, ImagemProdutoBackofficeDTO> {
    @Override
    protected void configure() {
        map().setId(source.getIdImagem());
        map().setNomeArquivo(source.getNomeArquivo());
        map().setCaminho(source.getCaminho());
        map().setImagemPrincipal(source.isImagemPrincipal());
    }
}