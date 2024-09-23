package com.conexao_digital.frontoffice.config.mapping.dto;

import org.modelmapper.PropertyMap;

import com.conexao_digital.frontoffice.dto.ImagemProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.entity.backoffice.ImagemProdutoBackofficeEntity;

public class ImagemProdutoFrontofficeDTOMap extends PropertyMap<ImagemProdutoBackofficeEntity, ImagemProdutoFrontofficeDTO> {
    @Override
    protected void configure() {
        map().setId(source.getIdImagem());
        map().setNomeArquivo(source.getNomeArquivo());
        map().setCaminho(source.getCaminho());
        map().setImagemPrincipal(source.isImagemPrincipal());
    }
}