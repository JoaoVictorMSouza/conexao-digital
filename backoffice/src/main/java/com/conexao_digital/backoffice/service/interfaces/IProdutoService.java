package com.conexao_digital.backoffice.service.interfaces;

import java.util.List;
import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;

public interface IProdutoService {
    void criarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
    List<ProdutoBackofficeDTO> listarProdutosBackOffice();
    ProdutoBackofficeDTO buscarProdutoPorId(int id);
    void editarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
    void editarStatusProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
}
