package com.conexao_digital.frontoffice.service.interfaces;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;

public interface ICarrinhoService {
    void removerProduto(CarrinhoDTO carrinhoDTO, int idProduto);
    void adicionarProduto(CarrinhoDTO carrinhoDTO, int idProduto);
    CarrinhoDTO inicializarCarrinho();
    void atualizarQuantidadeItem(CarrinhoDTO carrinhoDTO, int idProduto, int quantidade);
    CarrinhoDTO getCarrinho();
    void calcularFrete(CarrinhoDTO carrinhoDTO);
    void selecionarEndereco(CarrinhoDTO carrinhoDTO, int idEndereco, int idUsuario);
    void selecionarFormaPagamento(CarrinhoDTO carrinhoDTO, int idFormaPagamento);
    void limparCarrinho(CarrinhoDTO carrinhoDTO);
}
