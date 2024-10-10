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

import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.frontoffice.exception.EnderecoFrontofficeException;
import com.conexao_digital.frontoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;
import com.conexao_digital.frontoffice.service.interfaces.IEnderecoService;

@Controller
@RequestMapping("/endereco")
public class EnderecoFrontofficeController {
    private IEnderecoService enderecoService;
    private IAutenticacaoService autenticacaoService;
    private ICarrinhoService carrinhoService;

    @Autowired
    public EnderecoFrontofficeController(IEnderecoService iEnderecoService, IAutenticacaoService autenticacaoService, ICarrinhoService carrinhoService) {
        this.enderecoService = iEnderecoService;
        this.autenticacaoService = autenticacaoService;
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/criar/{idUsuario}")
    public String criarEnderecoFrontOffice(@PathVariable("idUsuario") int idUsuario, Model model) {
        model.addAttribute("carrinho", this.carrinhoService.getCarrinho());
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdUsuario(idUsuario);
        model.addAttribute("enderecoFrontoffice", enderecoDTO);
        return "enderecoFrontoffice/criar";
    }

    @PostMapping("/criar")
    public ResponseEntity<Map<String, String>> criarEnderecoFrontOffice(@ModelAttribute("enderecoFrontoffice") EnderecoDTO enderecoDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            this.enderecoService.criarEnderecoFrontOffice(enderecoDTO);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (EnderecoFrontofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }
    
    @PostMapping("/editarEnderecoPadrao")
    public ResponseEntity<Map<String, String>> editarEnderecoPadraoFrontOffice(@ModelAttribute("enderecoFrontoffice") EnderecoDTO enderecoDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            this.enderecoService.editarEnderecoPadraoFrontOffice(enderecoDTO);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (EnderecoFrontofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }
}