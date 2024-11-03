package com.conexao_digital.frontoffice.service.interfaces;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;
import com.conexao_digital.frontoffice.dto.PedidoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;

public interface IPedidoService {
    public PedidoDTO gerarPedido(CarrinhoDTO carrinhoDTO, UsuarioLogadoDTO usuarioLogado);
}
