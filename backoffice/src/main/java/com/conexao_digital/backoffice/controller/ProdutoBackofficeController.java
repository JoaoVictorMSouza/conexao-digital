package com.conexao_digital.backoffice.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.conexao_digital.backoffice.dto.ImagemProdutoBackofficeDTO;
import com.conexao_digital.backoffice.dto.ProdutoBackofficeDTO;
import com.conexao_digital.backoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.backoffice.entity.ProdutoBackofficeEntity;
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
    public String listarProdutoBackOffice(Model model) {
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        List<ProdutoBackofficeDTO> listaProdutosBackofficeDTO = this.produtoService.listarProdutosBackOffice();
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
    public ResponseEntity<Map<String, String>> criarUsuarioBackOffice(@ModelAttribute ProdutoBackofficeDTO produto,
                                @RequestParam("imagens") MultipartFile[] imagens,
                                @RequestParam("ordenacaoImagens") String ordenacaoImagens) {
        Map<String, String> response = new HashMap<>();

        try {
            // Salvar o produto 
            ProdutoBackofficeEntity produtoSalvo = this.produtoService.criarProdutoBackOffice(produto);

            // Converter a string de ordem em um array de inteiros
            String[] ordemArray = ordenacaoImagens.split(",");
            int[] ordemIndices = Arrays.stream(ordemArray).mapToInt(Integer::parseInt).toArray();

            for (int i = 0; i < ordemIndices.length; i++) {
                int index = ordemIndices[i];
                MultipartFile imagem = imagens[index];

                String nomeImagem = this.imagemProdutoService.gerarNomeImagem(produto.getNome());
                Path caminhoImagem = this.imagemProdutoService.retornarCaminhoImagem(nomeImagem);
                this.imagemProdutoService.salvarImagem(caminhoImagem, imagem.getBytes());

                ImagemProdutoBackofficeDTO imagemProduto = new ImagemProdutoBackofficeDTO();
                imagemProduto.setNomeArquivo(nomeImagem);
                imagemProduto.setCaminho(caminhoImagem.toString());
                imagemProduto.setImagemPrincipal(i == 0); // Definir a imagem principal para a primeira imagem da lista
                this.imagemProdutoService.salvarImagemBD(imagemProduto, produtoSalvo);
            }

            response.put("status", "OK");

            return ResponseEntity.ok(response);
        } catch (ProdutoBackofficeException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", e.getMessage());
            throw e;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response.put("status", "ERROR");
            response.put("mensagem", "Erro ao criar o produto");
            throw new ProdutoBackofficeException("Erro ao criar o produto");
        }
    }
}
