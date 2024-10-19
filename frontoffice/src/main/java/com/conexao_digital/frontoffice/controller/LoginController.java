package com.conexao_digital.frontoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private ICarrinhoService carrinhoService;

    public LoginController(ICarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String urlredirect = null;

        if (session != null) {
            urlredirect = (String) session.getAttribute("urlredirect");
            
            session.removeAttribute("urlredirect");
        }

        if (urlredirect != null && !urlredirect.isEmpty()) {
            model.addAttribute("urlRedirect", urlredirect);
        }

        model.addAttribute("carrinho", this.carrinhoService.getCarrinho());

        return "/login/login";
    }
}