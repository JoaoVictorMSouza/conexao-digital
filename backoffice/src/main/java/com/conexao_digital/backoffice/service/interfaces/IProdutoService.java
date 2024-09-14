package com.conexao_digital.backoffice.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;

public interface IProdutoService {
    void criarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO, MultipartFile[] imagens, String ordenacaoImagens);
    Page<ProdutoBackofficeDTO> listarProdutosBackOffice(int pagina, int quantidade);
    ProdutoBackofficeDTO buscarProdutoPorId(int id);
    void editarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO, MultipartFile[] imagens, String ordenacaoImagens, boolean isAdmin);
    void editarStatusProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
}
