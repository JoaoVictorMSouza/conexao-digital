package com.conexao_digital.frontoffice.repository.interfaces.frontoffice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;

public interface IUsuarioFrontofficeRepository extends JpaRepository<UsuarioFrontofficeEntity, Integer> {
    UsuarioFrontofficeEntity findByDsEmail(String email);
    UsuarioFrontofficeEntity findByIdUsuario(int id);
}
