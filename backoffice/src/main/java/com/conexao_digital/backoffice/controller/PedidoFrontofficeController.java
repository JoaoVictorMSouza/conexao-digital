package com.conexao_digital.backoffice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.conexao_digital.backoffice.dto.PedidoFrontofficeDTO;
import com.conexao_digital.backoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.backoffice.exception.UsuarioBackofficeException;
import com.conexao_digital.backoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.backoffice.service.interfaces.IPedidoFrontofficeService;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("editarpedido/{idPedido}")
    public String editarPedidoFrontoffice(Model model, @PathVariable("idPedido") Long idPedido) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        PedidoFrontofficeDTO pedidoFrontoffice = pedidoFrontofficeService.buscarPedidoFrontofficePorId(idPedido);
        model.addAttribute("pedidoFrontoffice", pedidoFrontoffice);

        return "pedidoFrontoffice/editarPedido";
    }

    @PostMapping("editarpedido")
    public ResponseEntity<Map<String, String>> editarPedidoFrontoffice(@ModelAttribute("pedidoFrontoffice") PedidoFrontofficeDTO pedidoFrontoffice) {
        Map<String, String> response = new HashMap<>();

        try {
            this.pedidoFrontofficeService.editarPedidoFrontoffice(pedidoFrontoffice);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (UsuarioBackofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }
    
}