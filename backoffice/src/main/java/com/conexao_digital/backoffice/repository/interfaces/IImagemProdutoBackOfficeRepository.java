package com.conexao_digital.backoffice.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexao_digital.backoffice.entity.ImagemProdutoBackofficeEntity;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;

public interface IImagemProdutoBackOfficeRepository extends JpaRepository<ImagemProdutoBackofficeEntity, Integer> {
    List<ImagemProdutoBackofficeEntity> findByProduto(ProdutoBackofficeEntity produtoBackofficeEntity);
}