package com.conexao_digital.frontoffice.repository.interfaces.frontoffice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexao_digital.frontoffice.entity.frontoffice.EnderecoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;

public interface IEnderecoFrontofficeRepository extends JpaRepository<EnderecoEntity, Integer> {
    EnderecoEntity findByUsuario(UsuarioFrontofficeEntity usuario);
    List<EnderecoEntity> findAllByUsuario(UsuarioFrontofficeEntity usuario);
    EnderecoEntity findByIdEnderecoAndUsuario(Integer idEndereco, UsuarioFrontofficeEntity usuario);
}
