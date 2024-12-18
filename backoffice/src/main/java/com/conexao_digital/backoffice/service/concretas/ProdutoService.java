package com.conexao_digital.backoffice.service.concretas;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;
import com.conexao_digital.backoffice.exception.ProdutoBackofficeException;
import com.conexao_digital.backoffice.repository.interfaces.IProdutoBackOfficeRepository;
import com.conexao_digital.backoffice.service.interfaces.IImagemProdutoService;
import com.conexao_digital.backoffice.service.interfaces.IProdutoService;

@Service
public class ProdutoService implements IProdutoService {
    private IProdutoBackOfficeRepository produtoRepository;
    private IImagemProdutoService imagemProdutoService;
    private ModelMapper modelMapper;

    @Autowired
    public ProdutoService(IProdutoBackOfficeRepository iProdutoRepository, ModelMapper modelMapper, IImagemProdutoService imagemProdutoService) {
        this.produtoRepository = iProdutoRepository;
        this.modelMapper = modelMapper;
        this.imagemProdutoService = imagemProdutoService;
    }

    public void criarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO, MultipartFile[] imagens, String ordenacaoImagens) {
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

        produtoBackofficeEntity.setDhCadastro(new Date());

        ProdutoBackofficeEntity produtoSalvo = produtoRepository.save(produtoBackofficeEntity);

        if (imagens != null && imagens.length > 0) {
            imagemProdutoService.salvarImagens(imagens, ordenacaoImagens, produtoSalvo);
        }
    }

    private ProdutoBackofficeDTO mapearProdutoBackofficeEntityParaProdutoBackofficeDTO(ProdutoBackofficeEntity produtoBackofficeEntity) {
        return modelMapper.map(produtoBackofficeEntity, ProdutoBackofficeDTO.class);
    }

    private ProdutoBackofficeDTO mapearProdutoBackofficeEntityParaPageProdutoBackofficeDTO(ProdutoBackofficeEntity produtoBackofficeEntity) {
        return modelMapper.map(produtoBackofficeEntity, ProdutoBackofficeDTO.class);
    }

    private ProdutoBackofficeEntity mapearProdutoBackofficeDTOParaProdutoBackofficeEntity(ProdutoBackofficeDTO produtoBackofficeDTO) {
        return modelMapper.map(produtoBackofficeDTO, ProdutoBackofficeEntity.class);
    }

    public Page<ProdutoBackofficeDTO> listarProdutosBackOffice(int pagina, int quantidade) {
        PageRequest pageRequest = PageRequest.of(pagina, quantidade, Sort.by(Sort.Direction.DESC, "dhCadastro"));

        Page<ProdutoBackofficeEntity> produtosBackofficeEntity = produtoRepository.findAll(pageRequest);

        Page<ProdutoBackofficeDTO> listaProdutosBackofficeDTO = produtosBackofficeEntity
                .map(this::mapearProdutoBackofficeEntityParaProdutoBackofficeDTO);

        return listaProdutosBackofficeDTO;
    }

    public ProdutoBackofficeDTO buscarProdutoPorId(int id) {
        ProdutoBackofficeEntity produtoBackofficeEntity = produtoRepository.findByIdProduto(id);

        if (produtoBackofficeEntity == null) {
            throw new ProdutoBackofficeException("Produto não encontrado");
        }

        return this.mapearProdutoBackofficeEntityParaProdutoBackofficeDTO(produtoBackofficeEntity);
    }

    public void editarProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO, MultipartFile[] imagens, String ordenacaoImagens, boolean isAdmin) {
        ProdutoBackofficeEntity produtoBackofficeEntity = produtoRepository.findByIdProduto(produtoBackofficeDTO.getId());

        if (produtoBackofficeEntity == null) {
            throw new ProdutoBackofficeException("Produto não encontrado");
        }

        if (isAdmin) {
            if (produtoBackofficeDTO.getNome() != null 
            && !produtoBackofficeDTO.getNome().trim().isEmpty() 
            && !produtoBackofficeDTO.getNome().equals(produtoBackofficeEntity.getDsNome())) {
                if (produtoBackofficeDTO.getNome().length() > 200) {
                    throw new ProdutoBackofficeException("Nome do produto não pode ter mais de 200 caracteres");
                }

                produtoBackofficeEntity.setDsNome(produtoBackofficeDTO.getNome());
            }

            if (produtoBackofficeDTO.getDescricaoDetalhada() != null 
            && !produtoBackofficeDTO.getDescricaoDetalhada().trim().isEmpty() 
            && !produtoBackofficeDTO.getDescricaoDetalhada().equals(produtoBackofficeEntity.getDsDetalhe())) {
                if (produtoBackofficeDTO.getDescricaoDetalhada().length() > 2000) {
                    throw new ProdutoBackofficeException("Descrição detalhada do produto não pode ter mais de 2000 caracteres");
                }

                produtoBackofficeEntity.setDsDetalhe(produtoBackofficeDTO.getDescricaoDetalhada());
            }

            if (produtoBackofficeDTO.getPreco() >= 0 
            && produtoBackofficeDTO.getPreco() != produtoBackofficeEntity.getVlPreco()) {
                produtoBackofficeEntity.setVlPreco(produtoBackofficeDTO.getPreco());
            }

            if (produtoBackofficeDTO.getAvaliacao() >= 0 
            && produtoBackofficeDTO.getAvaliacao() <= 5 
            && produtoBackofficeDTO.getAvaliacao() != produtoBackofficeEntity.getVlAvaliacao()) {
                produtoBackofficeEntity.setVlAvaliacao(produtoBackofficeDTO.getAvaliacao());
            }

            if (produtoBackofficeDTO.getQuantidadeEstoque() >= 0 && produtoBackofficeDTO.getQuantidadeEstoque() != produtoBackofficeEntity.getQtdEstoque()) {
                produtoBackofficeEntity.setQtdEstoque(produtoBackofficeDTO.getQuantidadeEstoque());
            }
        }

        if(produtoBackofficeDTO.getQuantidadeEstoque() > 0 && produtoBackofficeDTO.getQuantidadeEstoque() != produtoBackofficeEntity.getQtdEstoque()){
            produtoBackofficeEntity.setQtdEstoque(produtoBackofficeDTO.getQuantidadeEstoque());
        }

        ProdutoBackofficeEntity produtoSalvo = produtoRepository.save(produtoBackofficeEntity);

        imagemProdutoService.editarImagens(imagens, ordenacaoImagens, produtoBackofficeEntity);
    }

    public void editarStatusProdutoBackOffice(ProdutoBackofficeDTO produtoBackofficeDTO) {
        ProdutoBackofficeEntity produtoBackofficeEntity = produtoRepository.findByIdProduto(produtoBackofficeDTO.getId());

        if (produtoBackofficeEntity == null) {
            throw new ProdutoBackofficeException("Produto não encontrado");
        }

        produtoBackofficeEntity.setAtivo(produtoBackofficeDTO.isAtivo());

        produtoRepository.save(produtoBackofficeEntity);
    }
}