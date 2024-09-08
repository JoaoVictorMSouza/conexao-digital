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

    public void criarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO) {
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