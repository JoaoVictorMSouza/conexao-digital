package com.conexao_digital.frontoffice.repository.interfaces.backoffice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexao_digital.frontoffice.entity.backoffice.ProdutoBackofficeEntity;

public interface IProdutoBackOfficeRepository extends JpaRepository<ProdutoBackofficeEntity, Integer> {
    ProdutoBackofficeEntity findByIdProduto(int id);
}