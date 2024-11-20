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
import com.conexao_digital.frontoffice.entity.backoffice.ProdutoBackofficeEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.EnderecoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.ItemPedidoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.PedidoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;
import com.conexao_digital.frontoffice.enums.FormaPagamentoEnum;
import com.conexao_digital.frontoffice.enums.StatusPedidoEnum;
import com.conexao_digital.frontoffice.exception.PedidoFrontofficeException;
import com.conexao_digital.frontoffice.repository.interfaces.backoffice.IProdutoBackOfficeRepository;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IEnderecoFrontofficeRepository;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IPedidoFrontofficeRepository;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IUsuarioFrontofficeRepository;
import com.conexao_digital.frontoffice.service.interfaces.IPedidoService;

@Service
public class PedidoService implements IPedidoService {
    private IPedidoFrontofficeRepository pedidoRepository;
    private IUsuarioFrontofficeRepository usuarioRepository;
    private IEnderecoFrontofficeRepository enderecoRepository;
    private IProdutoBackOfficeRepository produtoRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PedidoService(IPedidoFrontofficeRepository iPedidoRepository,
                        IUsuarioFrontofficeRepository iUsuarioRepository,
                        IEnderecoFrontofficeRepository iEnderecoRepository,
                        IProdutoBackOfficeRepository iProdutoRepository,
                        ModelMapper modelMapper) {
        this.pedidoRepository = iPedidoRepository;
        this.usuarioRepository = iUsuarioRepository;
        this.enderecoRepository = iEnderecoRepository;
        this.produtoRepository = iProdutoRepository;
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

        StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.AGUARDANDO_PAGAMENTO;
        pedido.setIdStatusPagamento(statusPedidoEnum.getId());

        FormaPagamentoEnum formaPagamento = FormaPagamentoEnum.fromId(carrinhoDTO.getIdFormaPagamento());
        pedido.setIdFormaPagamento(formaPagamento.getId());

        UsuarioFrontofficeEntity usuario = usuarioRepository.findByIdUsuario(usuarioLogado.getId());
        pedido.setUsuario(usuario);

        EnderecoEntity endereco = this.enderecoRepository.findByIdEnderecoAndUsuario(carrinhoDTO.getEndereco().getIdEndereco(), usuario);

        pedido.setEnderecoEntrega(endereco);
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

        if (pedido.getEnderecoEntrega() == null) {
            throw new PedidoFrontofficeException("Pedido sem endereço de entrega atrelado.");
        }

        if (pedido.getEnderecoEntrega().getIdEndereco() <= 0) {
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

            if (item.getProduto() == null) {
                throw new PedidoFrontofficeException("Item do pedido sem produto atrelado."); 
            }

            if (item.getProduto().getIdProduto() <= 0) {
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
            ProdutoBackofficeEntity produto = this.produtoRepository.findByIdProduto(item.getProduto().getId());
            itemPedido.setProduto(produto);
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
        PedidoDTO pedido = modelMapper.map(pedidoEntity, PedidoDTO.class);
        pedido.setDsStatusPedido(this.getMensagemStatusPedido(pedido.getStatusPedidoEnum()));
        return pedido;
    }

    private ItemPedidoEntity mapearItemCarrinhoDTOParaItemPedidoEntity(ItemCarrinhoDTO itemCarrinhoDTO) {
        return modelMapper.map(itemCarrinhoDTO, ItemPedidoEntity.class);
    }

    public List<PedidoDTO> listarPedidosPorUsuario(int idUsuario) {
        if (idUsuario <= 0) {
            throw new PedidoFrontofficeException("ID do usuário inválido.");
        }

        UsuarioFrontofficeEntity usuario = usuarioRepository.findByIdUsuario(idUsuario);
        if (usuario == null) {
            throw new PedidoFrontofficeException("Usuário não encontrado.");
        }

        List<PedidoEntity> pedidos = pedidoRepository.findByUsuario(usuario);

        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(this::mapearPedidoEntityParaPedidoDTO)
                .toList();

        return pedidosDTO;
    }

    private String getMensagemStatusPedido(StatusPedidoEnum statusPedidoEnum) {
        switch (statusPedidoEnum) {
            case AGUARDANDO_PAGAMENTO:
                return "Aguardando pagamento";
            case PAGAMENTO_REJEITADO:
                return "Pagamento rejeitado";
            case PAGAMENTO_SUCESSO:
                return "Pagamento com sucesso";
            case AGUARDANDO_RETIRADA:
                return "Aguardando retirada";
            case EM_TRANSITO:
                return "Em transito";
            case ENTREGUE:
                return "Entregue";
            default:
                return "Status de pedido inválido";
        }
    }

    public PedidoDTO buscarPedidoPorId(Long idPedido, int idUsuario) {
        if (idUsuario <= 0) {
            throw new PedidoFrontofficeException("ID do usuário inválido.");
        }

        UsuarioFrontofficeEntity usuario = usuarioRepository.findByIdUsuario(idUsuario);

        if (usuario == null) {
            throw new PedidoFrontofficeException("Usuário não encontrado.");
        }

        if (idPedido <= 0) {
            throw new PedidoFrontofficeException("ID do pedido inválido.");
        }

        PedidoEntity pedido = pedidoRepository.findByIdPedidoAndUsuario(idPedido, usuario);

        if (pedido == null) {
            throw new PedidoFrontofficeException("Pedido não encontrado.");
        }

        return this.mapearPedidoEntityParaPedidoDTO(pedido);
    }
}
