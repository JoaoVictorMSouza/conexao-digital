package com.conexao_digital.frontoffice.service.concretas;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexao_digital.frontoffice.dto.ImagemProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.entity.backoffice.ImagemProdutoBackofficeEntity;
import com.conexao_digital.frontoffice.entity.backoffice.ProdutoBackofficeEntity;
import com.conexao_digital.frontoffice.exception.ImagemProdutoFrontofficeException;
import com.conexao_digital.frontoffice.repository.interfaces.backoffice.IImagemProdutoBackOfficeRepository;
import com.conexao_digital.frontoffice.repository.interfaces.backoffice.IProdutoBackOfficeRepository;
import com.conexao_digital.frontoffice.service.interfaces.IImagemProdutoService;

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

    private final String UPLOADDIR =  System.getProperty("user.home") + "/ecommerce/images"; 

    private ImagemProdutoFrontofficeDTO mapearImagemProdutoBackofficeEntityParaImagemProdutoFrontofficeDTO(ImagemProdutoBackofficeEntity imagemprodutoBackofficeDTO) {
        return modelMapper.map(imagemprodutoBackofficeDTO, ImagemProdutoFrontofficeDTO.class);
    }

    public Path retornarCaminhoImagem(String nomeImagem) {
        Path caminhoImagem = Paths.get(this.UPLOADDIR, nomeImagem);
        return caminhoImagem;
    }

    public List<ImagemProdutoFrontofficeDTO> listarImagensPorProdutoId(int idProduto) {
        ProdutoBackofficeEntity produtoBackofficeEntity = this.produtoBackOfficeRepository.findByIdProduto(idProduto);

        if (produtoBackofficeEntity == null) {
            throw new ImagemProdutoFrontofficeException("Produto não encontrado");
        }

        List<ImagemProdutoBackofficeEntity> imagensProduto = this.imagemProdutoBackOfficeRepository.findByProduto(produtoBackofficeEntity);
        List<ImagemProdutoFrontofficeDTO> imagensProdutoDTO = imagensProduto.stream()
            .sorted((img1, img2) -> Boolean.compare(img2.isImagemPrincipal(), img1.isImagemPrincipal()))
            .map(this::mapearImagemProdutoBackofficeEntityParaImagemProdutoFrontofficeDTO)
            .toList();
            
        return imagensProdutoDTO;
    }

    public ImagemProdutoFrontofficeDTO buscarImagemPrincipalPorProdutoId(int idProduto) {
        ProdutoBackofficeEntity produtoBackofficeEntity = this.produtoBackOfficeRepository.findByIdProduto(idProduto);

        if (produtoBackofficeEntity == null) {
            throw new ImagemProdutoFrontofficeException("Produto não encontrado");
        }

        ImagemProdutoBackofficeEntity imagemProdutoBackofficeEntity = this.imagemProdutoBackOfficeRepository.findByProdutoAndImagemPrincipal(produtoBackofficeEntity, true);

        if (imagemProdutoBackofficeEntity == null) {
            return null;
        }

        ImagemProdutoFrontofficeDTO imagemProdutoFrontofficeDTO = this.mapearImagemProdutoBackofficeEntityParaImagemProdutoFrontofficeDTO(imagemProdutoBackofficeEntity);

        return imagemProdutoFrontofficeDTO;
    }

    public String retornarUploadDir() {
        return this.UPLOADDIR;
    }
}