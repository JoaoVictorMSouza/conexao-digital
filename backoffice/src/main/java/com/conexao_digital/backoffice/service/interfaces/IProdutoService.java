package com.conexao_digital.backoffice.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;

public interface IProdutoService {
    void criarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO, MultipartFile[] imagens, String ordenacaoImagens);
    List<ProdutoBackofficeDTO> listarProdutosBackOffice();
    ProdutoBackofficeDTO buscarProdutoPorId(int id);
    void editarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
    void editarStatusProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
}
