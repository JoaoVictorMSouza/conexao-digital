package com.conexao_digital.frontoffice.service.interfaces;

import com.conexao_digital.frontoffice.dto.UsuarioFrontofficeDTO;

public interface IUsuarioService {
    void criarUsuarioFrontOffice(UsuarioFrontofficeDTO usuarioFrontofficeDTO);
    boolean verificarExistenciaEmail(String email);
    UsuarioFrontofficeDTO buscarUsuarioFrontOfficePorId(int id);
    void editarUsuarioFrontOffice(UsuarioFrontofficeDTO usuarioFrontofficeDTO);
}
