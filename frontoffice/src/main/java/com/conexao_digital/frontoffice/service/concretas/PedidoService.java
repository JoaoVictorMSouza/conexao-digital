package com.conexao_digital.frontoffice.service.concretas;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;
import com.conexao_digital.frontoffice.dto.ItemCarrinhoDTO;
import com.conexao_digital.frontoffice.dto.PedidoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.ItemPedidoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.PedidoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;
import com.conexao_digital.frontoffice.enums.FormaPagamentoEnum;
import com.conexao_digital.frontoffice.enums.StatusPagamentoEnum;
import com.conexao_digital.frontoffice.exception.PedidoFrontofficeException;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IPedidoFrontofficeRepository;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IUsuarioFrontofficeRepository;
import com.conexao_digital.frontoffice.service.interfaces.IPedidoService;

@Service
public class PedidoService implements IPedidoService {
    private IPedidoFrontofficeRepository pedidoRepository;
    private IUsuarioFrontofficeRepository usuarioRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PedidoService(IPedidoFrontofficeRepository iPedidoRepository,
                        IUsuarioFrontofficeRepository iUsuarioRepository,
                        ModelMapper modelMapper) {
        this.pedidoRepository = iPedidoRepository;
        this.usuarioRepository = iUsuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PedidoDTO gerarPedido(CarrinhoDTO carrinhoDTO, UsuarioLogadoDTO usuarioLogado) {
        PedidoEntity pedido = new PedidoEntity();

        this.montarPedido(carrinhoDTO, pedido, usuarioLogado);
        this.validarPedido(pedido);

        this.montarItensPedido(carrinhoDTO, pedido);
        this.validarItensPedido(pedido.getItens());

        PedidoEntity pedidoCriado = pedidoRepository.save(pedido);
        PedidoDTO pedidoDTO = this.mapearPedidoEntityParaPedidoDTO(pedidoCriado);

        return pedidoDTO;
    }

    private void montarPedido(CarrinhoDTO carrinhoDTO, PedidoEntity pedido, UsuarioLogadoDTO usuarioLogado) {
        pedido.setVlTotal(this.calcularValorTotal(carrinhoDTO));
        pedido.setVlSubtotal(this.calcularSubTotal(carrinhoDTO));
        pedido.setVlFrete(carrinhoDTO.getValorFrete());

        pedido.setDhPedido(new Date());

        StatusPagamentoEnum statusPagamento = StatusPagamentoEnum.AGUARDANDO_PAGAMENTO;
        pedido.setIdStatusPagamento(statusPagamento.getId());

        FormaPagamentoEnum formaPagamento = FormaPagamentoEnum.fromId(carrinhoDTO.getIdFormaPagamento());
        pedido.setIdFormaPagamento(formaPagamento.getId());

        UsuarioFrontofficeEntity usuario = usuarioRepository.findByIdUsuario(usuarioLogado.getId());
        pedido.setUsuario(usuario);

        pedido.setIdEnderecoEntrega(carrinhoDTO.getEndereco().getIdEndereco());
    }

    private void validarValores(PedidoEntity pedido) {
        if (pedido.getVlTotal() < 0) {
            throw new PedidoFrontofficeException("O valor total do pedido não pode ser negativo.");
        }

        if (pedido.getVlSubtotal() < 0) {
            throw new PedidoFrontofficeException("O valor subtotal do pedido não pode ser negativo.");
        }

        if (pedido.getVlFrete() < 0) {
            throw new PedidoFrontofficeException("O valor do frete do pedido não pode ser negativo.");
        }
    }

    private void validarPedido(PedidoEntity pedido) {
        if (pedido == null) {
            throw new PedidoFrontofficeException("Pedido não encontrado.");
        }

        this.validarValores(pedido);

        if (pedido.getUsuario() == null) {
            throw new PedidoFrontofficeException("Pedido sem usuário.");
        }

        if (pedido.getIdEnderecoEntrega() <= 0) {
            throw new PedidoFrontofficeException("Pedido sem endereço de entrega.");
        }

        if (pedido.getIdFormaPagamento() <= 0) {
            throw new PedidoFrontofficeException("Pedido sem forma de pagamento.");
        }

        if (pedido.getIdStatusPagamento() <= 0) {
            throw new PedidoFrontofficeException("Pedido sem status de pagamento.");
        }

        if (pedido.getDhPedido() == null) {
            throw new PedidoFrontofficeException("Pedido sem data de pedido.");
        }
    }

    private void validarItensPedido(List<ItemPedidoEntity> itensPedido) {
        if (itensPedido == null || itensPedido.isEmpty()) {
            throw new PedidoFrontofficeException("Pedido sem itens.");
        }

        for (ItemPedidoEntity item : itensPedido) {
            if (item.getNrQuantidade() <= 0) {
                throw new PedidoFrontofficeException("Item do pedido com quantidade inválida.");
            }

            if (item.getPedido() == null) {
                throw new PedidoFrontofficeException("Item do pedido sem pedido atrelado.");
            }

            if (item.getIdProduto() <= 0) {
                throw new PedidoFrontofficeException("Item do pedido sem produto.");
            }

            if (item.getVlPrecoUnitario() < 0) {
                throw new PedidoFrontofficeException("Item do pedido com preço inválido.");
            }
        }
    }

    private void montarItensPedido(CarrinhoDTO carrinhoDTO, PedidoEntity pedido) {
        List<ItemPedidoEntity> itensPedido = new ArrayList<>();

        for (ItemCarrinhoDTO item : carrinhoDTO.getItens()) {
            ItemPedidoEntity itemPedido = this.mapearItemCarrinhoDTOParaItemPedidoEntity(item);
            itemPedido.setPedido(pedido);
            itensPedido.add(itemPedido);
        }

        pedido.setItens(itensPedido);
    }

    private double calcularValorTotal(CarrinhoDTO carrinhoDTO) {
        double valorTotal = this.calcularSubTotal(carrinhoDTO);

        valorTotal += carrinhoDTO.getValorFrete();
        
        return valorTotal;
    }

    private double calcularSubTotal(CarrinhoDTO carrinhoDTO) {
        double valorTotal = 0;

        for (ItemCarrinhoDTO item : carrinhoDTO.getItens()) {
            valorTotal += item.getProduto().getPreco() * item.getQuantidade();
        }
        
        return valorTotal;
    }

    private PedidoDTO mapearPedidoEntityParaPedidoDTO(PedidoEntity pedidoEntity) {
        return modelMapper.map(pedidoEntity, PedidoDTO.class);
    }

    private PedidoEntity mapearPedidoDTOParaPedidoEntity(PedidoDTO pedidoDTO) {
        return modelMapper.map(pedidoDTO, PedidoEntity.class);
    }

    private ItemPedidoEntity mapearItemCarrinhoDTOParaItemPedidoEntity(ItemCarrinhoDTO itemCarrinhoDTO) {
        return modelMapper.map(itemCarrinhoDTO, ItemPedidoEntity.class);
    }
}
