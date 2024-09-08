package com.conexao_digital.backoffice.service.interfaces;

import java.nio.file.Path;

import com.conexao_digital.backoffice.dto.ImagemProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;

public interface IImagemProdutoService {
    void salvarImagem(Path caminhoImagem, byte[] imagem);
    Path retornarCaminhoImagem(String nomeImagem);
    String gerarNomeImagem(String nomeProduto);
    void salvarImagemBD(ImagemProdutoBackofficeDTO imagemProdutoBackofficeDTO, ProdutoBackofficeEntity produtoBackofficeEntity);
}
