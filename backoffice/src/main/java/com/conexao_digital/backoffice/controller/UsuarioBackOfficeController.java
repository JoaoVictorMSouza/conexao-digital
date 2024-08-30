package com.conexao_digital.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.conexao_digital.backoffice.model.UsuarioBackOffice;
import com.conexao_digital.backoffice.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioBackOfficeController {

    private IUsuarioService usuarioService;
    
    @Autowired
    public UsuarioBackOfficeController(IUsuarioService iUsuarioService) {
        this.usuarioService = iUsuarioService;
    }

    @GetMapping("/listarUsuariosBackOffice")
    public String listarUsuariosBackOffice() {
        return "usuarioBackOffice/listarUsuariosBackOffice";
    }

    @GetMapping("/criar")
    public String criarUsuarioBackOffice(Model model) {
        model.addAttribute("usuarioBackOffice", new UsuarioBackOffice());
        return "usuarioBackOffice/criar";
    }

    @PostMapping("/criar")
    public String criarUsuarioBackOffice(@ModelAttribute UsuarioBackOffice usuarioBackOffice) {
        // Lógica para salvar o usuário
        return "redirect:/usuarioBackOffice";
    }
}
