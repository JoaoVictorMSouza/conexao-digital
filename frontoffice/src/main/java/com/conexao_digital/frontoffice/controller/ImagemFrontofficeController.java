package com.conexao_digital.frontoffice.controller;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.conexao_digital.frontoffice.service.interfaces.IImagemProdutoService;

@Controller
@RequestMapping("/imagem")
public class ImagemFrontofficeController {
    private IImagemProdutoService imagemProdutoService;

    @Autowired
    public ImagemFrontofficeController(IImagemProdutoService iImagemProdutoService) {
        this.imagemProdutoService = iImagemProdutoService;
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) throws FileNotFoundException {
        try {
            String uploadDir = this.imagemProdutoService.retornarUploadDir();
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
            } else {
                Path notFoundfile = Paths.get("src/main/resources/static/images").resolve("sem-foto.jpg");
                Resource notFoundResource = new UrlResource(notFoundfile.toUri());

                if (notFoundResource.exists() || notFoundResource.isReadable()) {
                    return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(notFoundResource);
                }

                throw new FileNotFoundException("Arquivo não encontrado");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao carregar a imagem", e);
        }
    }
}
