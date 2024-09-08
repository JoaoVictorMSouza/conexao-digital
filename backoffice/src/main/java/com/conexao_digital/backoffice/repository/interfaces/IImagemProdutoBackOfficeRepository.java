package com.conexao_digital.backoffice.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexao_digital.backoffice.entity.ImagemProdutoBackofficeEntity;

public interface IImagemProdutoBackOfficeRepository extends JpaRepository<ImagemProdutoBackofficeEntity, Integer> {
    
}