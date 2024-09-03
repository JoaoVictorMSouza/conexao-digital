package com.conexao_digital.backoffice.repository.interfaces;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.conexao_digital.backoffice.entity.UsuarioBackofficeEntity;

public interface IUsuarioBackOfficeRepository extends JpaRepository<UsuarioBackofficeEntity, Integer> {
    UsuarioBackofficeEntity findByDsEmail(String email);
    List<UsuarioBackofficeEntity> findAll();
    UsuarioBackofficeEntity findByIdUsuario(int id);
}