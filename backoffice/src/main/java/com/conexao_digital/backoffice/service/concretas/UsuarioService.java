package com.conexao_digital.backoffice.service.concretas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexao_digital.backoffice.model.UsuarioBackOffice;
import com.conexao_digital.backoffice.repository.interfaces.IUsuarioBackOfficeRepository;
import com.conexao_digital.backoffice.service.interfaces.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {
    private IUsuarioBackOfficeRepository usuarioRepository;

    @Autowired
    public UsuarioService(IUsuarioBackOfficeRepository iUsuarioRepository) {
        this.usuarioRepository = iUsuarioRepository;
    }

    public UsuarioBackOffice createUsuarioBackOffice(UsuarioBackOffice usuario) {
        return usuarioRepository.save(usuario);
    }
}