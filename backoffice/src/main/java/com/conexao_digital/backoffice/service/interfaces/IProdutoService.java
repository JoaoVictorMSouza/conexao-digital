package com.conexao_digital.backoffice.service.interfaces;

import java.util.List;
import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;

public interface IProdutoService {
    ProdutoBackofficeEntity criarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
    List<ProdutoBackofficeDTO> listarProdutosBackOffice();
    ProdutoBackofficeDTO buscarProdutoPorId(int id);
    void editarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
    void editarStatusProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO);
}
