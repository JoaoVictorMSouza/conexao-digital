package com.conexao_digital.frontoffice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.conexao_digital.frontoffice.dto.ImagemProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.dto.ProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;
import com.conexao_digital.frontoffice.service.interfaces.IImagemProdutoService;
import com.conexao_digital.frontoffice.service.interfaces.IProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoFrontofficeController {
    private IProdutoService produtoService;
    private IImagemProdutoService imagemProdutoService;
    private ICarrinhoService carrinhoService;
    
    @Autowired
    public ProdutoFrontofficeController(IProdutoService iprodutoService, IImagemProdutoService iImagemProdutoService, ICarrinhoService carrinhoService) {
        this.produtoService = iprodutoService;
        this.imagemProdutoService = iImagemProdutoService;
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/visualizar/{id}")
    public String visualizarProdutoFrontOffice(@PathVariable("id") int id, Model model) {
        ProdutoFrontofficeDTO produtoFrontofficeDTO = this.produtoService.buscarProdutoPorId(id);
        model.addAttribute("produtoFrontoffice", produtoFrontofficeDTO);
        
        List<ImagemProdutoFrontofficeDTO> listaImagens = this.imagemProdutoService.listarImagensPorProdutoId(produtoFrontofficeDTO.getId());
        model.addAttribute("imagensProdutoFrontoffice", listaImagens);
        model.addAttribute("carrinho", this.carrinhoService.getCarrinho());

        return "produtoFrontoffice/visualizar";
    }
}
