package com.conexao_digital.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.conexao_digital.backoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.backoffice.service.interfaces.IAutenticacaoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    private IAutenticacaoService autenticacaoService;

    @Autowired
    public HomeController(IAutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        // Atribuindo sessão do usuário
        session.setAttribute("usuárioLogadoSession", usuarioLogado);

        return "index";
    }
}
