package com.conexao_digital.frontoffice.service.interfaces;

import java.nio.file.Path;
import java.util.List;

import com.conexao_digital.frontoffice.dto.ImagemProdutoFrontofficeDTO;

public interface IImagemProdutoService {
    Path retornarCaminhoImagem(String nomeImagem);
    List<ImagemProdutoFrontofficeDTO> listarImagensPorProdutoId(int produtoId);
    ImagemProdutoFrontofficeDTO buscarImagemPrincipalPorProdutoId(int idProduto);
    String retornarUploadDir();
}
