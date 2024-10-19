package com.conexao_digital.frontoffice.repository.interfaces.frontoffice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexao_digital.frontoffice.entity.frontoffice.PedidoEntity;

public interface IPedidoFrontofficeRepository extends JpaRepository<PedidoEntity, Long> {
}
