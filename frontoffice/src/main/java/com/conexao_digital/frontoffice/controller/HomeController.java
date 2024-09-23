package com.conexao_digital.frontoffice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.conexao_digital.frontoffice.dto.ImagemProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.dto.ProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.service.interfaces.IImagemProdutoService;
import com.conexao_digital.frontoffice.service.interfaces.IProdutoService;

import org.springframework.ui.Model;

@Controller
public class HomeController {
    private IProdutoService produtoService;
    private IImagemProdutoService imagemProdutoService;
    
    @Autowired
    public HomeController(IProdutoService iprodutoService, IImagemProdutoService iImagemProdutoService) {
        this.produtoService = iprodutoService;
        this.imagemProdutoService = iImagemProdutoService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "0") int page) {
        // Page<ProdutoFrontofficeDTO> listaProdutosFrontofficeDTO = produtoService.listarProdutosPaginadoFrontOffice(page, 10);
        List<ProdutoFrontofficeDTO> listaProdutosFrontofficeDTO = produtoService.listarProdutosFrontOffice();

        for (ProdutoFrontofficeDTO produtoFrontofficeDTO : listaProdutosFrontofficeDTO) {
            ImagemProdutoFrontofficeDTO imagemProdutoFrontofficeDTO = imagemProdutoService.buscarImagemPrincipalPorProdutoId(produtoFrontofficeDTO.getId());
            if (imagemProdutoFrontofficeDTO != null) {
                produtoFrontofficeDTO.setNomeImagemPrincipal(imagemProdutoFrontofficeDTO.getNomeArquivo());
                produtoFrontofficeDTO.setCaminhoImagemPrincipal(imagemProdutoFrontofficeDTO.getCaminho());
            } else {
                produtoFrontofficeDTO.setNomeImagemPrincipal("sem-imagem.jpg");
                produtoFrontofficeDTO.setCaminhoImagemPrincipal("/imagem/sem-imagem.jpg");
            }
        }

        model.addAttribute("listaProdutosFrontoffice", listaProdutosFrontofficeDTO);

        return "index";
    }
}
