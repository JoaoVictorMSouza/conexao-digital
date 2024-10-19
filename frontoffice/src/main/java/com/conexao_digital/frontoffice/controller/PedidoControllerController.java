package com.conexao_digital.frontoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;
import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.dto.UsuarioFrontofficeDTO;
import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.frontoffice.enums.EnderecoTipoEnum;
import com.conexao_digital.frontoffice.service.interfaces.IAutenticacaoService;
import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;
import com.conexao_digital.frontoffice.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/pedido")
public class PedidoControllerController {
    private IAutenticacaoService autenticacaoService;
    private ICarrinhoService carrinhoService;
    private IUsuarioService usuarioService;

    @Autowired
    public PedidoControllerController(IAutenticacaoService autenticacaoService, 
                                        ICarrinhoService carrinhoService,
                                        IUsuarioService usuarioService) {
        this.autenticacaoService = autenticacaoService;
        this.carrinhoService = carrinhoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/checkout")
    public String criarEnderecoFrontOffice(Model model) {
        CarrinhoDTO carrinho = carrinhoService.getCarrinho();
        model.addAttribute("carrinho", carrinho);
        UsuarioLogadoDTO usuarioLogado = autenticacaoService.retornarUsuarioLogado();
        model.addAttribute("usuarioLogado", usuarioLogado);

        UsuarioFrontofficeDTO usuarioFrontofficeDTO = usuarioService.buscarUsuarioFrontOfficePorId(usuarioLogado.getId());
        model.addAttribute("usuarioFrontoffice", usuarioFrontofficeDTO);


        if (carrinho.getEndereco() == null) {
            EnderecoDTO enderecoDTO = usuarioFrontofficeDTO.getEnderecos().stream()
                                            .filter(endereco -> endereco.getTipoEndereco().equals(EnderecoTipoEnum.ENTREGA) && endereco.isPadrao()).findFirst().orElse(null);
                                            
            carrinhoService.selecionarEndereco(carrinho, enderecoDTO.getIdEndereco(), usuarioFrontofficeDTO.getId());
        }

        return "pedido/checkout";
    }
}