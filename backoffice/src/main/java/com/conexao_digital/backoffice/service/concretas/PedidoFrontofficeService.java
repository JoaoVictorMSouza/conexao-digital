package com.conexao_digital.backoffice.service.concretas;

import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexao_digital.backoffice.dto.PedidoFrontofficeDTO;
import com.conexao_digital.backoffice.entity.PedidoFrontofficeEntity;
import com.conexao_digital.backoffice.enums.StatusPedidoEnum;
import com.conexao_digital.backoffice.exception.PedidoFrontofficeException;
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
        pedido.setDsStatusPedidoEnum(this.getMensagemStatusPedido(pedido.getStatusPedidoEnum()));
        return pedido;
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

    @Override
    public PedidoFrontofficeDTO buscarPedidoFrontofficePorId(Long idPedido) {
        PedidoFrontofficeEntity pedidoEntity = this.pedidoRepository.findById(idPedido).orElse(null);
        return this.mapearPedidoEntityParaPedidoDTO(pedidoEntity);
    }

    @Override
    public void editarPedidoFrontoffice(PedidoFrontofficeDTO pedidoFrontoffice) {
        if (pedidoFrontoffice.getIdStatusPedidoEnum() <= 0) {
            throw new PedidoFrontofficeException("Status de pedido inválido");
        }

        if (pedidoFrontoffice.getIdPedido() <= 0) {
            throw new PedidoFrontofficeException("Id do pedido inválido");
        }

        if (StatusPedidoEnum.fromId(pedidoFrontoffice.getIdStatusPedidoEnum()) == null) {
            throw new PedidoFrontofficeException("Status de pedido inválido");
        }

        PedidoFrontofficeEntity pedidoEntity = this.pedidoRepository.findById(pedidoFrontoffice.getIdPedido()).orElse(null);
        if (pedidoEntity == null) {
            throw new PedidoFrontofficeException("Pedido não encontrado");
        }
        pedidoEntity.setIdStatusPagamento(pedidoFrontoffice.getIdStatusPedidoEnum());
        this.pedidoRepository.save(pedidoEntity);
    }

    
}
