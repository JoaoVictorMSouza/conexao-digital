package com.conexao_digital.frontoffice.service.interfaces;

import com.conexao_digital.frontoffice.dto.EnderecoDTO;

public interface IEnderecoService {
    void criarEnderecoFrontOffice(EnderecoDTO enderecoDTO);
    void editarEnderecoPadraoFrontOffice(EnderecoDTO enderecoDTO);
}
