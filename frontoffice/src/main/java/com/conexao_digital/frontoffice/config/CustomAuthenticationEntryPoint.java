package com.conexao_digital.frontoffice.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String urlredirect = request.getParameter("urlredirect");

        if (urlredirect != null && !urlredirect.isEmpty()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("urlredirect", urlredirect);
        }
        
        response.sendRedirect("/login");
    }
}
