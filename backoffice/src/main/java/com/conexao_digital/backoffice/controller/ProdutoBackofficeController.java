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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;
import com.conexao_digital.backoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.backoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.backoffice.service.interfaces.IProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoBackofficeController {
    private IProdutoService produtoService;
    private IAutenticacaoService autenticacaoService;
    
    @Autowired
    public ProdutoBackofficeController(IProdutoService produtoService, IAutenticacaoService autenticacaoService) {
        this.produtoService = produtoService;
        this.autenticacaoService = autenticacaoService;
    }

    @GetMapping("/listarProdutosBackoffice")
    public String listarProdutoBackOffice(Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        List<ProdutoBackofficeDTO> listaProdutosBackofficeDTO = this.produtoService.listarProdutosBackOffice();
        model.addAttribute("listaProdutosBackoffice", listaProdutosBackofficeDTO);
        return "produtoBackoffice/listarProdutoBackoffice";
    }

    @GetMapping("/criar")
    public String criarProdutoBackOffice(Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        ProdutoBackofficeDTO produtoBackofficeDTO = new ProdutoBackofficeDTO();
        model.addAttribute("produtoBackoffice", produtoBackofficeDTO);
        return "produtoBackoffice/criar";
    }
}
