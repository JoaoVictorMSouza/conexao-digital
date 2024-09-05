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

import com.conexao_digital.backoffice.dto.UsuarioBackofficeDTO;
import com.conexao_digital.backoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.backoffice.exception.UsuarioBackofficeException;
import com.conexao_digital.backoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.backoffice.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioBackofficeController {
    private IUsuarioService usuarioService;
    private IAutenticacaoService autenticacaoService;
    
    @Autowired
    public UsuarioBackofficeController(IUsuarioService iUsuarioService, IAutenticacaoService autenticacaoService) {
        this.usuarioService = iUsuarioService;
        this.autenticacaoService = autenticacaoService;
    }

    @GetMapping("/listarUsuariosBackOffice")
    public String listarUsuariosBackOffice(Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        List<UsuarioBackofficeDTO> listaUsuariosBackofficeDTO = this.usuarioService.listarUsuariosBackOffice();
        model.addAttribute("listaUsuariosBackoffice", listaUsuariosBackofficeDTO);
        return "usuarioBackOffice/listarUsuariosBackoffice";
    }

    @GetMapping("/criar")
    public String criarUsuarioBackOffice(Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        UsuarioBackofficeDTO usuarioBackofficeDTO = new UsuarioBackofficeDTO();
        model.addAttribute("usuarioBackoffice", usuarioBackofficeDTO);
        return "usuarioBackOffice/criar";
    }

    @PostMapping("/criar")
    public ResponseEntity<Map<String, String>> criarUsuarioBackOffice(@ModelAttribute UsuarioBackofficeDTO  usuarioBackofficeDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            this.usuarioService.criarUsuarioBackOffice(usuarioBackofficeDTO);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (UsuarioBackofficeException e) {
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
    public String editarUsuarioBackOffice(@PathVariable("id") int id, Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        UsuarioBackofficeDTO usuarioBackofficeDTO = this.usuarioService.buscarUsuarioPorId(id);
        model.addAttribute("usuarioBackoffice", usuarioBackofficeDTO);

        return "usuarioBackOffice/editar";
    }

    @PostMapping("/editar")
    public ResponseEntity<Map<String, String>> editarUsuarioBackOffice(@ModelAttribute("usuarioBackoffice") UsuarioBackofficeDTO usuarioBackofficeDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            this.usuarioService.editarUsuarioBackOffice(usuarioBackofficeDTO);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (UsuarioBackofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/mudarStatus")
    public String editarStatusUsuarioBackOffice(@ModelAttribute("usuarioBackoffice") UsuarioBackofficeDTO usuarioBackofficeDTO) {
        //CONSTRUIR TODA A LOGICA DE MUDANÇA DE STATUS
        return "usuarioBackOffice/listarUsuariosBackoffice";
    }
}
