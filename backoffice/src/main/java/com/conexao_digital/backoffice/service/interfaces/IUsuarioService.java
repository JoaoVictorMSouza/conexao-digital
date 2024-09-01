package com.conexao_digital.backoffice.service.interfaces;

import com.conexao_digital.backoffice.dto.UsuarioBackofficeDTO;

public interface IUsuarioService {
    UsuarioBackofficeDTO criarUsuarioBackOffice(UsuarioBackofficeDTO usuarioBackofficeDTO);
    boolean verificarExistenciaEmail(String email);
}
