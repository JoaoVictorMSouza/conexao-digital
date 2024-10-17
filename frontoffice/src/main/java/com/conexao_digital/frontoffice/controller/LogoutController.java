package com.conexao_digital.frontoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Lógica para deslogar o usuário, por exemplo, invalidar a sessão
        request.getSession().invalidate();
        
        // Redirecionar para a página inicial
        return "redirect:/carrinho";
    }
}