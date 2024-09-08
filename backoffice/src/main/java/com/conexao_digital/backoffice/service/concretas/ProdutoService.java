package com.conexao_digital.backoffice.service.concretas;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;
import com.conexao_digital.backoffice.exception.ProdutoBackofficeException;
import com.conexao_digital.backoffice.repository.interfaces.IProdutoBackOfficeRepository;
import com.conexao_digital.backoffice.service.interfaces.IProdutoService;

@Service
public class ProdutoService implements IProdutoService {
    private IProdutoBackOfficeRepository produtoRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProdutoService(IProdutoBackOfficeRepository iProdutoRepository, ModelMapper modelMapper) {
        this.produtoRepository = iProdutoRepository;
        this.modelMapper = modelMapper;
    }

    public ProdutoBackofficeEntity criarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO) {
        if (produtoBackofficeDTO.getNome() == null || produtoBackofficeDTO.getNome().trim().isEmpty()) {
            throw new ProdutoBackofficeException("Nome do produto não pode ser vazio");
        }

        if (produtoBackofficeDTO.getNome().length() > 200) {
            throw new ProdutoBackofficeException("Nome do produto não pode ter mais de 200 caracteres");
        }

        if (produtoBackofficeDTO.getDescricaoDetalhada().length() > 2000) {
            throw new ProdutoBackofficeException("Descrição detalhada do produto não pode ter mais de 2000 caracteres");
        }

        if (produtoBackofficeDTO.getPreco() < 0) {
            throw new ProdutoBackofficeException("Preço do produto não pode ser negativo");
        }

        if (produtoBackofficeDTO.getQuantidadeEstoque() < 0) {
            throw new ProdutoBackofficeException("Quantidade em estoque do produto não pode ser negativa");
        }

        if (produtoBackofficeDTO.getAvaliacao() < 0 || produtoBackofficeDTO.getAvaliacao() > 5) {
            throw new ProdutoBackofficeException("Avaliação do produto deve ser entre 0 e 5");
        }

        ProdutoBackofficeEntity produtoBackofficeEntity = this.mapearProdutoBackofficeDTOParaProdutoBackofficeEntity(produtoBackofficeDTO);

        return produtoRepository.save(produtoBackofficeEntity);
    }

    private ProdutoBackofficeDTO mapearProdutoBackofficeEntityParaProdutoBackofficeDTO(ProdutoBackofficeEntity produtoBackofficeEntity) {
        return modelMapper.map(produtoBackofficeEntity, ProdutoBackofficeDTO.class);
    }

    private ProdutoBackofficeEntity mapearProdutoBackofficeDTOParaProdutoBackofficeEntity(ProdutoBackofficeDTO produtoBackofficeDTO) {
        return modelMapper.map(produtoBackofficeDTO, ProdutoBackofficeEntity.class);
    }

    public List<ProdutoBackofficeDTO> listarProdutosBackOffice() {
        List<ProdutoBackofficeEntity> produtosBackofficeEntity = produtoRepository.findAll();

        List<ProdutoBackofficeDTO> listaProdutosBackofficeDTO = produtosBackofficeEntity.stream()
                .map(produtoBackofficeEntity -> this.mapearProdutoBackofficeEntityParaProdutoBackofficeDTO(produtoBackofficeEntity))
                .toList();

        return listaProdutosBackofficeDTO;
    }

    public ProdutoBackofficeDTO buscarProdutoPorId(int id) {
        ProdutoBackofficeEntity produtoBackofficeEntity = produtoRepository.findByIdProduto(id);

        if (produtoBackofficeEntity == null) {
            throw new ProdutoBackofficeException("Produto não encontrado");
        }

        return this.mapearProdutoBackofficeEntityParaProdutoBackofficeDTO(produtoBackofficeEntity);
    }

    public void editarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO){
        
    }

    public void editarStatusProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO) {
        
    }
}