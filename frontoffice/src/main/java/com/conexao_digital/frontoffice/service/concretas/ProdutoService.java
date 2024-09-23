package com.conexao_digital.frontoffice.service.concretas;

import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.conexao_digital.frontoffice.dto.ProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.entity.backoffice.ProdutoBackofficeEntity;
import com.conexao_digital.frontoffice.exception.ProdutoFrontofficeException;
import com.conexao_digital.frontoffice.repository.interfaces.backoffice.IProdutoBackOfficeRepository;
import com.conexao_digital.frontoffice.service.interfaces.IProdutoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@Service
public class ProdutoService implements IProdutoService {
    private IProdutoBackOfficeRepository produtoRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProdutoService(IProdutoBackOfficeRepository iProdutoRepository, ModelMapper modelMapper) {
        this.produtoRepository = iProdutoRepository;
        this.modelMapper = modelMapper;
    }

    private ProdutoFrontofficeDTO mapearProdutoBackofficeEntityParaProdutoFrontofficeDTO(ProdutoBackofficeEntity produtoBackofficeEntity) {
        return modelMapper.map(produtoBackofficeEntity, ProdutoFrontofficeDTO.class);
    }

    public Page<ProdutoFrontofficeDTO> listarProdutosPaginadoFrontOffice(int pagina, int quantidade) {
        PageRequest pageRequest = PageRequest.of(pagina, quantidade, Sort.by(Sort.Direction.DESC, "dhCadastro"));

        Page<ProdutoBackofficeEntity> produtosBackofficeEntity = produtoRepository.findAll(pageRequest);

        Page<ProdutoFrontofficeDTO> listaProdutosBackofficeDTO = produtosBackofficeEntity
                .map(this::mapearProdutoBackofficeEntityParaProdutoFrontofficeDTO);

        return listaProdutosBackofficeDTO;
    }

    public List<ProdutoFrontofficeDTO> listarProdutosFrontOffice() {
        List<ProdutoBackofficeEntity> produtosBackofficeEntity = produtoRepository.findAll();
    
        List<ProdutoFrontofficeDTO> listaProdutosBackofficeDTO = produtosBackofficeEntity
                .stream()
                .sorted(Comparator.comparing((ProdutoBackofficeEntity produtoBackofficeEntity) -> produtoBackofficeEntity.getDhCadastro()).reversed())
                .map(this::mapearProdutoBackofficeEntityParaProdutoFrontofficeDTO)
                .toList();
    
        return listaProdutosBackofficeDTO;
    }

    public ProdutoFrontofficeDTO buscarProdutoPorId(int id) {
        ProdutoBackofficeEntity produtoBackofficeEntity = produtoRepository.findByIdProduto(id);

        if (produtoBackofficeEntity == null) {
            throw new ProdutoFrontofficeException("Produto não encontrado");
        }

        return this.mapearProdutoBackofficeEntityParaProdutoFrontofficeDTO(produtoBackofficeEntity);
    }
}