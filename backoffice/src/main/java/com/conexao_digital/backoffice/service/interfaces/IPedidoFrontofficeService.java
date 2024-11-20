package com.conexao_digital.backoffice.service.interfaces;

import java.util.List;

import com.conexao_digital.backoffice.dto.PedidoFrontofficeDTO;

public interface IPedidoFrontofficeService {
    public List<PedidoFrontofficeDTO> listarPedidosFrontoffice();
    public PedidoFrontofficeDTO buscarPedidoFrontofficePorId(Long idPedido);
    public void editarPedidoFrontoffice(PedidoFrontofficeDTO pedidoFrontoffice);
}
