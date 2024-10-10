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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioFrontofficeDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.frontoffice.enums.EnderecoTipoEnum;
import com.conexao_digital.frontoffice.exception.UsuarioFrontofficeException;
import com.conexao_digital.frontoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;
import com.conexao_digital.frontoffice.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioFrontofficeController {
    private IUsuarioService usuarioService;
    private IAutenticacaoService autenticacaoService;
    private ICarrinhoService carrinhoService;

    @Autowired
    public UsuarioFrontofficeController(IUsuarioService iUsuarioService, IAutenticacaoService autenticacaoService, ICarrinhoService carrinhoService) {
        this.usuarioService = iUsuarioService;
        this.autenticacaoService = autenticacaoService;
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/criar")
    public String criarUsuarioFrontOffice(Model model) {
        model.addAttribute("carrinho", this.carrinhoService.getCarrinho());
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        UsuarioFrontofficeDTO usuarioFrontofficeDTO = new UsuarioFrontofficeDTO();
        model.addAttribute("usuarioFrontoffice", usuarioFrontofficeDTO);
        return "usuarioFrontoffice/criar";
    }

    @PostMapping("/criar")
    public ResponseEntity<Map<String, String>> criarUsuarioFrontOffice(@RequestBody UsuarioFrontofficeDTO  usuarioFrontofficeDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            this.usuarioService.criarUsuarioFrontOffice(usuarioFrontofficeDTO);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (UsuarioFrontofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/consultarEmail")
    public ResponseEntity<Map<String, Boolean>> consultarEmail(@RequestParam String email) {
        boolean emailExiste = this.usuarioService.verificarExistenciaEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("emailExiste", emailExiste);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioFrontOffice(@PathVariable("id") int id, Model model) {
        model.addAttribute("carrinho", this.carrinhoService.getCarrinho());
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        UsuarioFrontofficeDTO usuarioFrontofficeDTO = this.usuarioService.buscarUsuarioFrontOfficePorId(id);
        model.addAttribute("usuarioFrontoffice", usuarioFrontofficeDTO);

        List<EnderecoDTO> listaEnderecoEntrega = usuarioFrontofficeDTO.getEnderecos()
                                                                    .stream()
                                                                    .filter(endereco -> endereco.getTipoEndereco().equals(EnderecoTipoEnum.ENTREGA)).toList();

        model.addAttribute("listaEnderecoEntrega", listaEnderecoEntrega);

        return "usuarioFrontoffice/editar";
    }
    
    @PostMapping("/editar")
    public ResponseEntity<Map<String, String>> editarUsuarioFrontOffice(@ModelAttribute("usuarioFrontoffice") UsuarioFrontofficeDTO usuarioFrontofficeDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            this.usuarioService.editarUsuarioFrontOffice(usuarioFrontofficeDTO);

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