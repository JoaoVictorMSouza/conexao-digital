package com.conexao_digital.frontoffice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.frontoffice.exception.UsuarioFrontofficeException;
import com.conexao_digital.frontoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;

@Controller
@RequestMapping("/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {
    private ICarrinhoService carrinhoService;
    private IAutenticacaoService autenticacaoService;

    @Autowired
    public CarrinhoController(ICarrinhoService carrinhoService, IAutenticacaoService autenticacaoService) {
        this.carrinhoService = carrinhoService;
        this.autenticacaoService = autenticacaoService;
    }

    @ModelAttribute("carrinho")
    public CarrinhoDTO inicializarCarrinho() {
        return carrinhoService.inicializarCarrinho();
    }

    @GetMapping
    public String mostrarCarrinho(@ModelAttribute("carrinho") CarrinhoDTO carrinho, Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("carrinho", carrinho);
        return "carrinho/carrinho";
    }

    @PostMapping("/adicionar/{idProduto}")
    public String adicionarProduto(@ModelAttribute("carrinho") CarrinhoDTO carrinho,
                                   @PathVariable int idProduto) {
        carrinhoService.adicionarProduto(carrinho, idProduto);
        return "redirect:/carrinho";
    }

    @PostMapping("/remover/{idProduto}")
    public String removerProduto(@ModelAttribute("carrinho") CarrinhoDTO carrinho,
                                 @PathVariable int idProduto) {
        carrinhoService.removerProduto(carrinho, idProduto);
        return "redirect:/carrinho";
    }

    @PostMapping("/atualizarQuantidade/{idProduto}/{quantidade}")
    public String atualizarQuantidade(@ModelAttribute("carrinho") CarrinhoDTO carrinho,
                                 @PathVariable int idProduto, 
                                 @PathVariable int quantidade) {
        carrinhoService.atualizarQuantidadeItem(carrinho, idProduto, quantidade);
        return "redirect:/carrinho";
    }

    @PostMapping("/selecionarEndereco/{idEndereco}/{idUsuario}")
    public ResponseEntity<Map<String, String>> atualizarIdEnderecoCarrinho(@PathVariable int idEndereco, 
                                                                            @PathVariable int idUsuario, 
                                                                            @ModelAttribute("carrinho") CarrinhoDTO carrinho) {
        Map<String, String> response = new HashMap<>();

        try {        
            carrinhoService.selecionarEndereco(carrinho, idEndereco, idUsuario);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (UsuarioFrontofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/calcularFrete")
    public ResponseEntity<Map<String, String>> calcularFrete(@ModelAttribute("carrinho") CarrinhoDTO carrinho) {
        Map<String, String> response = new HashMap<>();

        try {
            this.carrinhoService.calcularFrete(carrinho); 

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (UsuarioFrontofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/selecionarFormaPagamento/{idFormaPagamento}")
    public ResponseEntity<Map<String, String>> selecionarFormaPagamento(@PathVariable int idFormaPagamento, 
                                                                        @ModelAttribute("carrinho") CarrinhoDTO carrinho) {
        Map<String, String> response = new HashMap<>();

        try {
            carrinhoService.selecionarFormaPagamento(carrinho, idFormaPagamento);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (UsuarioFrontofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }
}
