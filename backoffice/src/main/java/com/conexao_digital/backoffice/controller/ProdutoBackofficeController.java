package com.conexao_digital.backoffice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import com.conexao_digital.backoffice.dto.ImagemProdutoBackofficeDTO;
import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;
import com.conexao_digital.backoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.backoffice.enums.UsuarioGrupo;
import com.conexao_digital.backoffice.exception.ProdutoBackofficeException;
import com.conexao_digital.backoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.backoffice.service.interfaces.IImagemProdutoService;
import com.conexao_digital.backoffice.service.interfaces.IProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoBackofficeController {
    private IProdutoService produtoService;
    private IImagemProdutoService imagemProdutoService;
    private IAutenticacaoService autenticacaoService;
    
    @Autowired
    public ProdutoBackofficeController(IProdutoService iprodutoService, IImagemProdutoService iImagemProdutoService, IAutenticacaoService iAutenticacaoService) {
        this.produtoService = iprodutoService;
        this.imagemProdutoService = iImagemProdutoService;
        this.autenticacaoService = iAutenticacaoService;
    }

    @GetMapping("/listarProdutosBackoffice")
    public String listarProdutoBackOffice(
            Model model,
            @RequestParam(defaultValue = "0") int page) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        Page<ProdutoBackofficeDTO> listaProdutosBackofficeDTO = produtoService.listarProdutosBackOffice(page, 10);
        model.addAttribute("listaProdutosBackoffice", listaProdutosBackofficeDTO);
        return "produtoBackoffice/listarProdutoBackoffice";
    }

    @GetMapping("/criar")
    public String criarProdutoBackOffice(Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        ProdutoBackofficeDTO produtoBackofficeDTO = new ProdutoBackofficeDTO();
        model.addAttribute("produtoBackoffice", produtoBackofficeDTO);
        return "produtoBackoffice/criar";
    }

    @PostMapping("/criar")
    public ResponseEntity<Map<String, String>> criarProdutoBackOffice(@ModelAttribute("produtoBackoffice") ProdutoBackofficeDTO produto,
                                @RequestParam(name = "imagens", required = false) MultipartFile[] imagens,
                                @RequestParam(name = "ordenacaoImagens", required = false) String ordenacaoImagens) {
        Map<String, String> response = new HashMap<>();

        try {
            // Salvar o produto e imagens
            this.produtoService.criarProdutoBackOffice(produto, imagens, ordenacaoImagens);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (ProdutoBackofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/editar/{id}")
    public String editarProdutoBackOffice(@PathVariable("id") int id, Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        ProdutoBackofficeDTO produtoBackofficeDTO = this.produtoService.buscarProdutoPorId(id);
        model.addAttribute("produtoBackoffice", produtoBackofficeDTO);
        
        List<ImagemProdutoBackofficeDTO> listaImagens = this.imagemProdutoService.listarImagensPorProdutoId(produtoBackofficeDTO.getId());
        model.addAttribute("imagensProdutoBackoffice", listaImagens);

        return "produtoBackoffice/editar";
    }

    @PostMapping("/editar")
    public ResponseEntity<Map<String, String>> editarProdutoBackOffice(@ModelAttribute("produtoBackoffice") ProdutoBackofficeDTO produto,
                                @RequestParam(name = "imagens", required = false) MultipartFile[] imagens,
                                @RequestParam(name = "ordenacaoImagens", required = false) String ordenacaoImagens) {
        Map<String, String> response = new HashMap<>();

        try {
            UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();

            this.produtoService.editarProdutoBackOffice(produto, imagens, ordenacaoImagens, usuarioLogado.getUsuarioGrupo() == UsuarioGrupo.ADMIN);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (ProdutoBackofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/mudarStatus")
    public ResponseEntity<Map<String, String>> editarStatusProdutoBackOffice(@ModelAttribute("produtoBackoffice") ProdutoBackofficeDTO produtoBackofficeDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            this.produtoService.editarStatusProdutoBackOffice(produtoBackofficeDTO);

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (ProdutoBackofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        }
    }
}
