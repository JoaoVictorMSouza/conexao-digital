package com.conexao_digital.frontoffice.service.interfaces;

import java.util.List;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;
import com.conexao_digital.frontoffice.dto.PedidoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;

public interface IPedidoService {
    public PedidoDTO gerarPedido(CarrinhoDTO carrinhoDTO, UsuarioLogadoDTO usuarioLogado);
    public List<PedidoDTO> listarPedidosPorUsuario(int idUsuario);
}
