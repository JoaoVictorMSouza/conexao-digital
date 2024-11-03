package com.conexao_digital.frontoffice.service.concretas;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.conexao_digital.frontoffice.dto.CarrinhoDTO;
import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.dto.ItemCarrinhoDTO;
import com.conexao_digital.frontoffice.dto.ProdutoFrontofficeDTO;
import com.conexao_digital.frontoffice.entity.backoffice.ImagemProdutoBackofficeEntity;
import com.conexao_digital.frontoffice.entity.backoffice.ProdutoBackofficeEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.EnderecoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;
import com.conexao_digital.frontoffice.exception.CarrinhoFrontofficeException;
import com.conexao_digital.frontoffice.repository.interfaces.backoffice.IProdutoBackOfficeRepository;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IUsuarioFrontofficeRepository;
import com.conexao_digital.frontoffice.service.interfaces.ICarrinhoService;

import jakarta.servlet.http.HttpSession;

@Service
public class CarrinhoService implements ICarrinhoService {
    private IProdutoBackOfficeRepository produtoBackOfficeRepository;
    private IUsuarioFrontofficeRepository usuarioFrontofficeRepository;
    private ModelMapper modelMapper;


    @Autowired
    public CarrinhoService(IProdutoBackOfficeRepository produtoBackOfficeRepository, IUsuarioFrontofficeRepository usuarioFrontofficeRepository, ModelMapper modelMapper) {
        this.produtoBackOfficeRepository = produtoBackOfficeRepository;
        this.usuarioFrontofficeRepository = usuarioFrontofficeRepository;
        this.modelMapper = modelMapper;
    }

    public void removerProduto(CarrinhoDTO carrinhoDTO, int idProduto) {
        if (!(carrinhoDTO.getItens().stream().anyMatch(item -> item.getProduto().getId() == idProduto))) {
            throw new CarrinhoFrontofficeException("Produto não encontrado");
        }
        carrinhoDTO.getItens().removeIf(item -> item.getProduto().getId() == idProduto);
        this.calcularCarrinho(carrinhoDTO);
    }

    public void adicionarProduto(CarrinhoDTO carrinhoDTO, int idProduto) {
        ProdutoBackofficeEntity produto = produtoBackOfficeRepository.findByIdProduto(idProduto);

        if (produto == null) {
            throw new CarrinhoFrontofficeException("Produto não encontrado");
        }

        if (carrinhoDTO.getItens().stream().anyMatch(item -> item.getProduto().getId() == idProduto)) {
            ItemCarrinhoDTO itemCarrinhoDTO = carrinhoDTO.getItens().stream().filter(item -> item.getProduto().getId() == idProduto).findFirst().get();
            this.atualizarQuantidadeItem(carrinhoDTO, idProduto, itemCarrinhoDTO.getQuantidade() + 1);

            this.calcularCarrinho(carrinhoDTO);
            return;
        }

        ProdutoFrontofficeDTO produtoFrontofficeDTO = mapearProdutoBackofficeEntityParaProdutoFrontofficeDTO(produto);

        if (produto.getImagens() != null && produto.getImagens().size() > 0 && produto.getImagens().stream().anyMatch(imagem -> imagem.isImagemPrincipal())) {
            ImagemProdutoBackofficeEntity imagemPrincipal = produto.getImagens().stream().filter(imagem -> imagem.isImagemPrincipal()).findFirst().get();

            if (imagemPrincipal != null) {
                produtoFrontofficeDTO.setNomeImagemPrincipal(imagemPrincipal.getNomeArquivo());
                produtoFrontofficeDTO.setCaminhoImagemPrincipal(imagemPrincipal.getCaminho());
            }
            
        } else {
            produtoFrontofficeDTO.setNomeImagemPrincipal("sem-imagem.jpg");
            produtoFrontofficeDTO.setCaminhoImagemPrincipal("/imagem/sem-imagem.jpg");
        }

        ItemCarrinhoDTO itemCarrinhoDTO = new ItemCarrinhoDTO(produtoFrontofficeDTO, 1);

        carrinhoDTO.getItens().add(itemCarrinhoDTO);
        this.calcularCarrinho(carrinhoDTO);
    }

    private void calcularCarrinho(CarrinhoDTO carrinhoDTO){
        this.atualizarValorTotalItens(carrinhoDTO);
        this.atualizarQuantidadeTotalItem(carrinhoDTO);
        this.atualizarValorTotal(carrinhoDTO);
    }

    private ProdutoFrontofficeDTO mapearProdutoBackofficeEntityParaProdutoFrontofficeDTO(ProdutoBackofficeEntity produtoBackofficeEntity) {
        return modelMapper.map(produtoBackofficeEntity, ProdutoFrontofficeDTO.class);
    }

    private void atualizarValorTotalItens(CarrinhoDTO carrinhoDTO) {
        double valorTotal = carrinhoDTO.getItens().stream().mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade()).sum();
        carrinhoDTO.setValorTotalItens(valorTotal);
    }

    private void atualizarValorTotal(CarrinhoDTO carrinhoDTO) {
        double valorTotal = carrinhoDTO.getValorTotalItens() + carrinhoDTO.getValorFrete();
        carrinhoDTO.setValorTotal(valorTotal);
    }

    public CarrinhoDTO inicializarCarrinho() {
        return new CarrinhoDTO();
    }

    public void atualizarQuantidadeItem(CarrinhoDTO carrinhoDTO, int idProduto, int quantidade) {
        carrinhoDTO.getItens().stream().filter(item -> item.getProduto().getId() == idProduto).findFirst().ifPresent(item -> item.setQuantidade(quantidade));

        this.calcularCarrinho(carrinhoDTO);
    }

    private void atualizarQuantidadeTotalItem(CarrinhoDTO carrinhoDTO) {
        carrinhoDTO.setQuantidadeItens(carrinhoDTO.getItens().stream().mapToInt(ItemCarrinhoDTO::getQuantidade).sum());
    }

    public CarrinhoDTO getCarrinho() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        CarrinhoDTO carrinho = (CarrinhoDTO) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = this.inicializarCarrinho(); // Crie um novo carrinho se não existir
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    public void calcularFrete(CarrinhoDTO carrinhoDTO) {
        carrinhoDTO.setValorFrete(carrinhoDTO.getValorFrete());
        this.calcularCarrinho(carrinhoDTO);
    }

    public void selecionarEndereco(CarrinhoDTO carrinhoDTO, int idEndereco, int idUsuario) {
        UsuarioFrontofficeEntity usuario = usuarioFrontofficeRepository.findByIdUsuario(idUsuario);
        if (usuario == null) {
            throw new CarrinhoFrontofficeException("Usuário não encontrado");
        }

        if (usuario.getEnderecos() == null || usuario.getEnderecos().size() == 0) {
            throw new CarrinhoFrontofficeException("Usuário não possui endereços cadastrados");
        }

        if (usuario.getEnderecos().stream().noneMatch(endereco -> endereco.getIdEndereco() == idEndereco)) {
            throw new CarrinhoFrontofficeException("Endereço não encontrado");
        }
        
        EnderecoEntity enderecoPadraoEntity = usuario.getEnderecos().stream().filter(endereco -> endereco.getIdEndereco() == idEndereco).findFirst().get();
        EnderecoDTO enderecoPadraoDTO = this.mapearEnderecoEntityParaEnderecoDTO(enderecoPadraoEntity);

        carrinhoDTO.setEndereco(enderecoPadraoDTO);
    }

    private EnderecoDTO mapearEnderecoEntityParaEnderecoDTO(EnderecoEntity enderecoEntity) {
        return modelMapper.map(enderecoEntity, EnderecoDTO.class);
    }

    public void selecionarFormaPagamento(CarrinhoDTO carrinhoDTO, int idFormaPagamento) {
        if (idFormaPagamento <= 0) {
            throw new CarrinhoFrontofficeException("Forma de pagamento inválida");
        }
        carrinhoDTO.setIdFormaPagamento(idFormaPagamento);
    }

    public void limparCarrinho(CarrinhoDTO carrinhoDTO) {
        carrinhoDTO.getItens().clear();
        carrinhoDTO.setEndereco(null);
        carrinhoDTO.setIdFormaPagamento(0);
        carrinhoDTO.setValorFrete(0);
        carrinhoDTO.setValorTotal(0);
        carrinhoDTO.setValorTotalItens(0);
        carrinhoDTO.setQuantidadeItens(0);
        carrinhoDTO.setCep(null);
    }
}
