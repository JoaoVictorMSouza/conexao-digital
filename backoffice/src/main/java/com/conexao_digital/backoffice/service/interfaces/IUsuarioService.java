package com.conexao_digital.backoffice.service.interfaces;

import java.util.List;

import com.conexao_digital.backoffice.dto.UsuarioBackofficeDTO;

public interface IUsuarioService {
    void criarUsuarioBackOffice(UsuarioBackofficeDTO usuarioBackofficeDTO);
    boolean verificarExistenciaEmail(String email);
    List<UsuarioBackofficeDTO> listarUsuariosBackOffice();
    UsuarioBackofficeDTO buscarUsuarioPorId(int id);
    void editarUsuarioBackOffice(UsuarioBackofficeDTO usuarioBackofficeDTO);
    void editarStatusUsuarioBackOffice(UsuarioBackofficeDTO usuarioBackofficeDTO);
}
