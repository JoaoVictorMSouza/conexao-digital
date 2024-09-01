package com.conexao_digital.backoffice.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import com.conexao_digital.backoffice.entity.UsuarioBackofficeEntity;


public interface IUsuarioBackOfficeRepository extends JpaRepository<UsuarioBackofficeEntity, Integer> {
    UsuarioBackofficeEntity findByDsEmail(String email);
}