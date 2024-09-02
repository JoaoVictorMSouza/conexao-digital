package com.conexao_digital.backoffice.controller;

import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.conexao_digital.backoffice.dto.UsuarioBackofficeDTO;
import com.conexao_digital.backoffice.exception.UsuarioBackofficeException;
import com.conexao_digital.backoffice.service.interfaces.IUsuarioService;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;

@Controller
@RequestMapping("/usuario")
public class UsuarioBackofficeController {
    private IUsuarioService usuarioService;
    
    @Autowired
    public UsuarioBackofficeController(IUsuarioService iUsuarioService) {
        this.usuarioService = iUsuarioService;
    }

    @GetMapping("/listarUsuariosBackOffice")
    public String listarUsuariosBackOffice(Model model) {
        List<UsuarioBackofficeDTO> listaUsuariosBackofficeDTO = this.usuarioService.listarUsuariosBackOffice();
        model.addAttribute("listaUsuariosBackoffice", listaUsuariosBackofficeDTO);
        return "usuarioBackOffice/listarUsuariosBackoffice";
    }

    @GetMapping("/criar")
    public String criarUsuarioBackOffice(Model model) {
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
    
}
