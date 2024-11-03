package com.conexao_digital.frontoffice.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;
import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.dto.PedidoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioFrontofficeDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.frontoffice.enums.EnderecoTipoEnum;
import com.conexao_digital.frontoffice.exception.PedidoFrontofficeException;
import com.conexao_digital.frontoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;
import com.conexao_digital.frontoffice.service.interfaces.IPedidoService;
import com.conexao_digital.frontoffice.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/pedido")
public class PedidoControllerController {
    private IAutenticacaoService autenticacaoService;
    private ICarrinhoService carrinhoService;
    private IUsuarioService usuarioService;
    private IPedidoService pedidoService;

    @Autowired
    public PedidoControllerController(IAutenticacaoService autenticacaoService, 
                                        ICarrinhoService carrinhoService,
                                        IUsuarioService usuarioService,
                                        IPedidoService pedidoService) {
        this.autenticacaoService = autenticacaoService;
        this.carrinhoService = carrinhoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        CarrinhoDTO carrinho = carrinhoService.getCarrinho();
        model.addAttribute("carrinho", carrinho);
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        UsuarioFrontofficeDTO usuarioFrontofficeDTO = usuarioService.buscarUsuarioFrontOfficePorId(usuarioLogado.getId());
        model.addAttribute("usuarioFrontoffice", usuarioFrontofficeDTO);

        if (carrinho.getEndereco() == null) {
            EnderecoDTO enderecoDTO = usuarioFrontofficeDTO.getEnderecos().stream()
                                            .filter(endereco -> endereco.getTipoEndereco().equals(EnderecoTipoEnum.ENTREGA) && endereco.isPadrao()).findFirst().orElse(new EnderecoDTO());
                                            
            carrinhoService.selecionarEndereco(carrinho, enderecoDTO.getIdEndereco(), usuarioFrontofficeDTO.getId());
        }

        return "pedido/checkout";
    }

    @GetMapping("/resumo")
    public String visualizarResumoPedido(Model model) {
        CarrinhoDTO carrinho = carrinhoService.getCarrinho();
        model.addAttribute("carrinho", carrinho);


        if (carrinho.getIdFormaPagamento() <= 0) {
            return "redirect:/pedido/checkout";            
        }

        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        UsuarioFrontofficeDTO usuarioFrontofficeDTO = usuarioService.buscarUsuarioFrontOfficePorId(usuarioLogado.getId());
        model.addAttribute("usuarioFrontoffice", usuarioFrontofficeDTO);

        return "pedido/resumo";
    }

    @PostMapping("/finalizar")
    public ResponseEntity<Map<String, String>> finalizarPedido() {
        Map<String, String> response = new HashMap<>();

        try {
            CarrinhoDTO carrinho = carrinhoService.getCarrinho();
            UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();

            PedidoDTO pedidoDTO = this.pedidoService.gerarPedido(carrinho, usuarioLogado);

            carrinhoService.limparCarrinho(carrinho);

            response.put("status", "OK");
            response.put("valortotal", String.valueOf(pedidoDTO.getValorTotal()));
            response.put("numeropedido", String.valueOf(pedidoDTO.getIdPedido()));

            return ResponseEntity.ok(response);
        } catch (PedidoFrontofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/meuspedidos")
    public String meusPedidos(Model model) {
        CarrinhoDTO carrinho = carrinhoService.getCarrinho();
        model.addAttribute("carrinho", carrinho);

        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "pedido/meuspedidos";
    }
}