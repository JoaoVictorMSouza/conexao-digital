package com.conexao_digital.backoffice.service.concretas;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexao_digital.backoffice.dto.ImagemProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ImagemProdutoBackofficeEntity;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;
import com.conexao_digital.backoffice.exception.ImagemProdutoBackofficeException;
import com.conexao_digital.backoffice.repository.interfaces.IImagemProdutoBackOfficeRepository;
import com.conexao_digital.backoffice.service.interfaces.IImagemProdutoService;

@Service
public class ImagemProdutoService implements IImagemProdutoService {
    private IImagemProdutoBackOfficeRepository imagemProdutoBackOfficeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ImagemProdutoService(IImagemProdutoBackOfficeRepository iImagemProdutoBackOfficeRepository, ModelMapper modelMapper) {
        this.imagemProdutoBackOfficeRepository = iImagemProdutoBackOfficeRepository;
        this.modelMapper = modelMapper;
    }

    private final String uploadDir = "src/main/resources/static/uploads/"; 

    public void salvarImagem(Path caminhoImagem, byte[] imagem) {
        try {
            // Criar o diretório se não existir
            if (!Files.exists(Paths.get(uploadDir))) {
                Files.createDirectories(Paths.get(uploadDir));
            }

            // Salvar a imagem no diretório
            Files.write(caminhoImagem, imagem);
        } catch (Exception e) {
            throw new ImagemProdutoBackofficeException("Erro ao salvar a imagem");
        }
    }

    public Path retornarCaminhoImagem(String nomeImagem) {
        Path caminhoImagem = Paths.get(uploadDir, nomeImagem);
        return caminhoImagem;
    }

    public String gerarNomeImagem(String nomeProduto) {
        String dataAtual = LocalDate.now().toString();
        String novoNome = UUID.randomUUID().toString() + "_" + nomeProduto + "_" + dataAtual;
        return novoNome;
    }

    private ImagemProdutoBackofficeDTO mapearImagemProdutoBackofficeEntityParaImagemProdutoBackofficeDTO(ImagemProdutoBackofficeEntity imagemprodutoBackofficeDTO) {
        return modelMapper.map(imagemprodutoBackofficeDTO, ImagemProdutoBackofficeDTO.class);
    }

    private ImagemProdutoBackofficeEntity mapearImagemProdutoBackofficeDTOParaImagemProdutoBackofficeEntity(ImagemProdutoBackofficeDTO imagemprodutoBackofficeDTO) {
        return modelMapper.map(imagemprodutoBackofficeDTO, ImagemProdutoBackofficeEntity.class);
    }

    public void salvarImagemBD(ImagemProdutoBackofficeDTO imagemProdutoBackofficeDTO, ProdutoBackofficeEntity produtoBackofficeEntity) {
        if (imagemProdutoBackofficeDTO.getCaminho() == null || imagemProdutoBackofficeDTO.getCaminho().isEmpty()) {
            throw new ImagemProdutoBackofficeException("Caminho da imagem não pode ser nulo ou vazio");
        }

        if (imagemProdutoBackofficeDTO.getNomeArquivo() == null || imagemProdutoBackofficeDTO.getNomeArquivo().isEmpty()) {
            throw new ImagemProdutoBackofficeException("Nome do arquivo da imagem não pode ser nulo ou vazio");
        }

        if (produtoBackofficeEntity == null) {
            throw new ImagemProdutoBackofficeException("Produto não pode ser nulo");
        }

        ImagemProdutoBackofficeEntity imagemProdutoBackofficeEntity = this.mapearImagemProdutoBackofficeDTOParaImagemProdutoBackofficeEntity(imagemProdutoBackofficeDTO);

        imagemProdutoBackofficeEntity.setProduto(produtoBackofficeEntity);

        this.imagemProdutoBackOfficeRepository.save(imagemProdutoBackofficeEntity);
    }
}