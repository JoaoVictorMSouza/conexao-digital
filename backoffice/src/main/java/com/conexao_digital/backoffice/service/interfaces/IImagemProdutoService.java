package com.conexao_digital.backoffice.service.interfaces;

import java.nio.file.Path;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.conexao_digital.backoffice.dto.ImagemProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;

public interface IImagemProdutoService {
    Path retornarCaminhoImagem(String nomeImagem);
    void salvarImagens(MultipartFile[] imagens, String ordenacaoImagens, ProdutoBackofficeEntity produtoBackofficeEntity);
    List<ImagemProdutoBackofficeDTO> listarImagensPorProdutoId(int produtoId);
    void editarImagens(MultipartFile[] imagens, String ordenacaoImagens, ProdutoBackofficeEntity produtoBackofficeEntity);
}
