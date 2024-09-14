package com.conexao_digital.backoffice.service.concretas;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.conexao_digital.backoffice.dto.ImagemProdutoBackofficeDTO;
import com.conexao_digital.backoffice.entity.ImagemProdutoBackofficeEntity;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;
import com.conexao_digital.backoffice.exception.ImagemProdutoBackofficeException;
import com.conexao_digital.backoffice.repository.interfaces.IImagemProdutoBackOfficeRepository;
import com.conexao_digital.backoffice.repository.interfaces.IProdutoBackOfficeRepository;
import com.conexao_digital.backoffice.service.interfaces.IImagemProdutoService;

@Service
public class ImagemProdutoService implements IImagemProdutoService {
    private IImagemProdutoBackOfficeRepository imagemProdutoBackOfficeRepository;
    private ModelMapper modelMapper;
    private IProdutoBackOfficeRepository produtoBackOfficeRepository;

    @Autowired
    public ImagemProdutoService(IImagemProdutoBackOfficeRepository iImagemProdutoBackOfficeRepository, ModelMapper modelMapper, IProdutoBackOfficeRepository produtoBackOfficeRepository) {
        this.imagemProdutoBackOfficeRepository = iImagemProdutoBackOfficeRepository;
        this.modelMapper = modelMapper;
        this.produtoBackOfficeRepository = produtoBackOfficeRepository;
    }

    private final String UPLOADDIR = "src/main/resources/static/uploads/"; 
    private final String EXTENCAO = ".jpg"; 

    private void salvarImagem(Path caminhoImagem, byte[] imagem) {
        try {
            // Criar o diretório se não existir
            if (!Files.exists(Paths.get(this.UPLOADDIR))) {
                Files.createDirectories(Paths.get(UPLOADDIR));
            }

            // Salvar a imagem no diretório
            Files.write(caminhoImagem, imagem);
        } catch (Exception e) {
            throw new ImagemProdutoBackofficeException("Erro ao salvar a imagem");
        }
    }

    private String gerarNomeImagem(int idProduto) {
        String dataAtual = LocalDate.now().toString();
        String novoNome = String.valueOf(idProduto) + "_" + dataAtual + "_" + UUID.randomUUID().toString() + this.EXTENCAO;
        return novoNome;
    }

    private ImagemProdutoBackofficeDTO mapearImagemProdutoBackofficeEntityParaImagemProdutoBackofficeDTO(ImagemProdutoBackofficeEntity imagemprodutoBackofficeDTO) {
        return modelMapper.map(imagemprodutoBackofficeDTO, ImagemProdutoBackofficeDTO.class);
    }

    private ImagemProdutoBackofficeEntity mapearImagemProdutoBackofficeDTOParaImagemProdutoBackofficeEntity(ImagemProdutoBackofficeDTO imagemprodutoBackofficeDTO) {
        return modelMapper.map(imagemprodutoBackofficeDTO, ImagemProdutoBackofficeEntity.class);
    }

    private void salvarImagemBD(ImagemProdutoBackofficeDTO imagemProdutoBackofficeDTO, ProdutoBackofficeEntity produtoBackofficeEntity) {
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

    public void salvarImagens(MultipartFile[] imagens, String ordenacaoImagens, ProdutoBackofficeEntity produtoBackofficeEntity) {
        // Converter a string de ordem em um array de inteiros
        String[] ordemArray = ordenacaoImagens.split(",");
        int[] ordemIndices = Arrays.stream(ordemArray).mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < ordemIndices.length; i++) {
            int index = ordemIndices[i];
            MultipartFile imagem = imagens[index];

            String nomeImagem = this.gerarNomeImagem(produtoBackofficeEntity.getIdProduto());
            Path caminhoImagem = this.retornarCaminhoImagem(nomeImagem);

            try {
                this.salvarImagem(caminhoImagem, imagem.getBytes());
            } catch (IOException e) {
                throw new ImagemProdutoBackofficeException("Erro ao salvar a imagem");
            }

            ImagemProdutoBackofficeDTO imagemProduto = new ImagemProdutoBackofficeDTO();
            imagemProduto.setNomeArquivo(nomeImagem);
            imagemProduto.setCaminho(caminhoImagem.toString());
            imagemProduto.setImagemPrincipal(i == 0); // Definir a imagem principal para a primeira imagem da lista
            this.salvarImagemBD(imagemProduto, produtoBackofficeEntity);
        }
    }

    public Path retornarCaminhoImagem(String nomeImagem) {
        Path caminhoImagem = Paths.get(this.UPLOADDIR, nomeImagem);
        return caminhoImagem;
    }

    public List<ImagemProdutoBackofficeDTO> listarImagensPorProdutoId(int idProduto) {
        ProdutoBackofficeEntity produtoBackofficeEntity = this.produtoBackOfficeRepository.findByIdProduto(idProduto);

        if (produtoBackofficeEntity == null) {
            throw new ImagemProdutoBackofficeException("Produto não encontrado");
        }

        List<ImagemProdutoBackofficeEntity> imagensProduto = this.imagemProdutoBackOfficeRepository.findByProduto(produtoBackofficeEntity);
        return imagensProduto.stream().map(this::mapearImagemProdutoBackofficeEntityParaImagemProdutoBackofficeDTO).toList();
    }

    public void editarImagens(MultipartFile[] imagens, String ordenacaoImagens, ProdutoBackofficeEntity produtoBackofficeEntity) {
        //TODO: FAZER REGRA DE EDICAO DE IMAGENS
    }
}