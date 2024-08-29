package com.conexao_digital.backoffice.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import com.conexao_digital.backoffice.model.UsuarioBackOffice;


public interface IUsuarioBackOfficeRepository extends JpaRepository<UsuarioBackOffice, Integer> {
    
}