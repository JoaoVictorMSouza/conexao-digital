package com.conexao_digital.frontoffice.controller;

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

import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioFrontofficeDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.frontoffice.enums.EnderecoTipoEnum;
import com.conexao_digital.frontoffice.exception.EnderecoFrontofficeException;
import com.conexao_digital.frontoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;
import com.conexao_digital.frontoffice.service.interfaces.IEnderecoService;
import com.conexao_digital.frontoffice.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/endereco")
public class EnderecoFrontofficeController {
    private IEnderecoService enderecoService;
    private IAutenticacaoService autenticacaoService;
    private ICarrinhoService carrinhoService;
    private IUsuarioService usuarioService;

    @Autowired
    public EnderecoFrontofficeController(IEnderecoService iEnderecoService, 
                                        IAutenticacaoService autenticacaoService, 
                                        ICarrinhoService carrinhoService, 
                                        IUsuarioService iUsuarioService) {
        this.enderecoService = iEnderecoService;
        this.autenticacaoService = autenticacaoService;
        this.carrinhoService = carrinhoService;
        this.usuarioService = iUsuarioService;
    }

    @GetMapping("/criar/{idUsuario}")
    public String criarEnderecoFrontOffice(
                        @RequestParam(defaultValue = "", required = false, name = "urlredirect") String urlredirect, 
                        @PathVariable("idUsuario") int idUsuario, Model model) {
        if (urlredirect != null && !urlredirect.isEmpty()) {
            model.addAttribute("urlRedirect", urlredirect);
        }

        model.addAttribute("carrinho", this.carrinhoService.getCarrinho());
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdUsuario(idUsuario);
        model.addAttribute("enderecoFrontoffice", enderecoDTO);
        return "enderecoFrontoffice/criar";
    }

    @PostMapping("/criar")
    public ResponseEntity<Map<String, String>> criarEnderecoFrontOffice(            
                        @RequestParam(defaultValue = "", required = false, name = "urlredirect") String urlredirect, 
                        @ModelAttribute("enderecoFrontoffice") EnderecoDTO enderecoDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            this.enderecoService.criarEnderecoFrontOffice(enderecoDTO);

            response.put("status", "OK");
            
            if (urlredirect != null && !urlredirect.isEmpty()) {
                response.put("urlredirect", urlredirect);
            }

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

    @GetMapping("/enderecoEntrega/{idUsuario}")
    public String enderecoEntrega(@PathVariable("idUsuario") int idUsuario, Model model) {
        model.addAttribute("carrinho", this.carrinhoService.getCarrinho());
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        UsuarioFrontofficeDTO usuarioFrontofficeDTO = this.usuarioService.buscarUsuarioFrontOfficePorId(idUsuario);
        model.addAttribute("usuarioFrontoffice", usuarioFrontofficeDTO);

        List<EnderecoDTO> listaEnderecoEntrega = usuarioFrontofficeDTO.getEnderecos()
                                                                    .stream()
                                                                    .filter(endereco -> endereco.getTipoEndereco().equals(EnderecoTipoEnum.ENTREGA)).toList();

        model.addAttribute("listaEnderecoEntrega", listaEnderecoEntrega);

        return "enderecoFrontoffice/selecionacaoEnderecoEntrega";
    }
}