package com.conexao_digital.frontoffice.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.conexao_digital.frontoffice.dto.ProdutoFrontofficeDTO;

public interface IProdutoService {
    Page<ProdutoFrontofficeDTO> listarProdutosPaginadoFrontOffice(int pagina, int quantidade);
    List<ProdutoFrontofficeDTO> listarProdutosFrontOffice();
    ProdutoFrontofficeDTO buscarProdutoPorId(int id);
}
