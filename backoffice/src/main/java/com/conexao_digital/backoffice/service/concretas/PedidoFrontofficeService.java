package com.conexao_digital.backoffice.service.concretas;

import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexao_digital.backoffice.dto.PedidoFrontofficeDTO;
import com.conexao_digital.backoffice.entity.PedidoFrontofficeEntity;
import com.conexao_digital.backoffice.enums.StatusPagamentoEnum;
import com.conexao_digital.backoffice.repository.interfaces.IPedidoFrontofficeRepository;
import com.conexao_digital.backoffice.service.interfaces.IPedidoFrontofficeService;

@Service
public class PedidoFrontofficeService implements IPedidoFrontofficeService {
    private ModelMapper modelMapper;
    private IPedidoFrontofficeRepository pedidoRepository;

    @Autowired
    public PedidoFrontofficeService(ModelMapper modelMapper, IPedidoFrontofficeRepository pedidoRepository) {
        this.modelMapper = modelMapper;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public List<PedidoFrontofficeDTO> listarPedidosFrontoffice() {
        List<PedidoFrontofficeEntity> listaPedidos = this.pedidoRepository.findAll();

        List<PedidoFrontofficeDTO> pedidosDTO = listaPedidos
                .stream()
                .sorted(Comparator.comparing((PedidoFrontofficeEntity pedidoFrontofficeEntity) -> pedidoFrontofficeEntity.getDhPedido()).reversed())
                .map(this::mapearPedidoEntityParaPedidoDTO)
                .toList();

        return pedidosDTO;
    }

    private PedidoFrontofficeDTO mapearPedidoEntityParaPedidoDTO(PedidoFrontofficeEntity pedidoEntity) {
        PedidoFrontofficeDTO pedido = modelMapper.map(pedidoEntity, PedidoFrontofficeDTO.class);
        pedido.setDsStatusPagamento(this.getMensagemStatusPagamento(pedido.getStatusPagamento()));
        return pedido;
    }

    private String getMensagemStatusPagamento(StatusPagamentoEnum statusPagamento) {
        switch (statusPagamento) {
            case AGUARDANDO_PAGAMENTO:
                return "Aguardando pagamento";
                case PAGAMENTO_CONFIRMADO:
                return "Pagamento confirmado";
            case PAGAMENTO_CANCELADO:
                return "Pagamento cancelado";
            default:
                return "Status de pagamento inválido";
        }
    }
}
