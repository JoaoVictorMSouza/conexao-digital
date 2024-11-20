package com.conexao_digital.backoffice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.conexao_digital.backoffice.dto.PedidoFrontofficeDTO;
import com.conexao_digital.backoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.backoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.backoffice.service.interfaces.IPedidoFrontofficeService;

@Controller
@RequestMapping("/pedido")
public class PedidoFrontofficeController {
    private IPedidoFrontofficeService pedidoFrontofficeService;
    private IAutenticacaoService autenticacaoService;

    @Autowired
    public PedidoFrontofficeController(IPedidoFrontofficeService pedidoFrontofficeService, IAutenticacaoService autenticacaoService) {
        this.pedidoFrontofficeService = pedidoFrontofficeService;
        this.autenticacaoService = autenticacaoService;
    }

    @GetMapping("listarPedidosFrontoffice")
    public String listarPedidosFrontoffice(Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        List<PedidoFrontofficeDTO> listaPedidosFrontoffice = pedidoFrontofficeService.listarPedidosFrontoffice();
        model.addAttribute("listaPedidosFrontoffice", listaPedidosFrontoffice);
        return "pedidoFrontoffice/listarPedidos";
    }
}