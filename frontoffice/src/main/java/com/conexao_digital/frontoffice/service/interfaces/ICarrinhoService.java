package com.conexao_digital.frontoffice.service.interfaces;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;

public interface ICarrinhoService {
    void adicionarProduto(CarrinhoDTO carrinhoDTO, int idProduto);
    CarrinhoDTO inicializarCarrinho();
    void atualizarQuantidadeItem(CarrinhoDTO carrinhoDTO, int idProduto, int quantidade);
    CarrinhoDTO getCarrinho();
    void calcularFrete(CarrinhoDTO carrinhoDTO);
}
