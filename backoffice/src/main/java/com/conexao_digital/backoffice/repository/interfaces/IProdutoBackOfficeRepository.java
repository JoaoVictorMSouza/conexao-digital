package com.conexao_digital.backoffice.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;

public interface IProdutoBackOfficeRepository extends JpaRepository<ProdutoBackofficeEntity, Integer> {
    ProdutoBackofficeEntity findByIdProduto(int id);
}