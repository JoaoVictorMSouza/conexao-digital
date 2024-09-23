package com.conexao_digital.frontoffice.repository.interfaces.backoffice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexao_digital.frontoffice.entity.backoffice.ImagemProdutoBackofficeEntity;
import com.conexao_digital.frontoffice.entity.backoffice.ProdutoBackofficeEntity;

public interface IImagemProdutoBackOfficeRepository extends JpaRepository<ImagemProdutoBackofficeEntity, Integer> {
    List<ImagemProdutoBackofficeEntity> findByProduto(ProdutoBackofficeEntity produtoBackofficeEntity);
    ImagemProdutoBackofficeEntity findByProdutoAndImagemPrincipal(ProdutoBackofficeEntity produtoBackofficeEntity, boolean imagemPrincipal);
}