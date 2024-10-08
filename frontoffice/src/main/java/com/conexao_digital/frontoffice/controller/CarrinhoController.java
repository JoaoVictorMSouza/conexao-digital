package com.conexao_digital.frontoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/calcularFrete")
    public String calcularFrete(@ModelAttribute("carrinho") CarrinhoDTO carrinho) {
        //LOGICA DE CALCULO DE FRETE
        //LOGICA DE CALCULO DE FRETE
        //LOGICA DE CALCULO DE FRETE
        //LOGICA DE CALCULO DE FRETE
        //LOGICA DE CALCULO DE FRETE
        this.carrinhoService.calcularFrete(carrinho); 
        return "redirect:/carrinho";
    }
}
