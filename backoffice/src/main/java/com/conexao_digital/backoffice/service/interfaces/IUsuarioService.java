package com.conexao_digital.backoffice.service.interfaces;

import com.conexao_digital.backoffice.dto.UsuarioBackofficeDTO;
import java.util.List;

public interface IUsuarioService {
    void criarUsuarioBackOffice(UsuarioBackofficeDTO usuarioBackofficeDTO);
    boolean verificarExistenciaEmail(String email);
    List<UsuarioBackofficeDTO> listarUsuariosBackOffice();
}
