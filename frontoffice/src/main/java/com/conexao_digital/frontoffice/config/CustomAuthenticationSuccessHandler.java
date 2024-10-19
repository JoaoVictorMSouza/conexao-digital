package com.conexao_digital.frontoffice.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String urlredirect = request.getParameter("redirect");

        if (urlredirect != null && !urlredirect.isEmpty()) {
            response.sendRedirect(urlredirect);
            return;
        }
        
        response.sendRedirect("/");
    }
}
