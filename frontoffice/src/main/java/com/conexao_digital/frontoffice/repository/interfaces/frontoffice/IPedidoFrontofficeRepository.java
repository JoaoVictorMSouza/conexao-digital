package com.conexao_digital.frontoffice.repository.interfaces.frontoffice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexao_digital.frontoffice.entity.frontoffice.PedidoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;

import java.util.List;


public interface IPedidoFrontofficeRepository extends JpaRepository<PedidoEntity, Long> {
    List<PedidoEntity> findByUsuario(UsuarioFrontofficeEntity usuario);
    PedidoEntity findByIdPedidoAndUsuario(Long idPedido, UsuarioFrontofficeEntity usuario);
}
