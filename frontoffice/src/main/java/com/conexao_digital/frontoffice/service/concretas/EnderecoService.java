package com.conexao_digital.frontoffice.service.concretas;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexao_digital.frontoffice.dto.EnderecoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.EnderecoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;
import com.conexao_digital.frontoffice.exception.EnderecoFrontofficeException;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IEnderecoFrontofficeRepository;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IUsuarioFrontofficeRepository;
import com.conexao_digital.frontoffice.service.interfaces.IEnderecoService;

@Service
public class EnderecoService implements IEnderecoService {
    private IEnderecoFrontofficeRepository enderecoRepository;
    private IUsuarioFrontofficeRepository usuarioRepository;
    private ModelMapper modelMapper;

    @Autowired
    public EnderecoService(IEnderecoFrontofficeRepository iEnderecoRepository, IUsuarioFrontofficeRepository iUsuarioRepository, ModelMapper modelMapper) {
        this.enderecoRepository = iEnderecoRepository;
        this.usuarioRepository = iUsuarioRepository;
        this.modelMapper = modelMapper;
    }

    public void criarEnderecoFrontOffice(EnderecoDTO enderecoDTO) {
        UsuarioFrontofficeEntity usuarioFrontofficeEntity = usuarioRepository.findByIdUsuario(enderecoDTO.getIdUsuario());

        if (usuarioFrontofficeEntity == null) {
            throw new EnderecoFrontofficeException("Usuário não encontrado");
        }

        EnderecoEntity enderecoEntity = this.mapearEnderecoDTOParaEnderecoEntity(enderecoDTO);

        enderecoEntity.setUsuario(usuarioFrontofficeEntity);

        enderecoRepository.save(enderecoEntity);
    }

    private EnderecoEntity mapearEnderecoDTOParaEnderecoEntity(EnderecoDTO enderecoDTO) {
        return modelMapper.map(enderecoDTO, EnderecoEntity.class);
    }

    public void editarEnderecoPadraoFrontOffice(EnderecoDTO enderecoDTO) {
        UsuarioFrontofficeEntity usuarioFrontofficeEntity = usuarioRepository.findByIdUsuario(enderecoDTO.getIdUsuario());

        if (usuarioFrontofficeEntity == null) {
            throw new EnderecoFrontofficeException("Usuário não encontrado");
        }

        List<EnderecoEntity> listaEnderecoEntity = enderecoRepository.findAllByUsuario(usuarioFrontofficeEntity);

        if (listaEnderecoEntity.stream().noneMatch(enderecoEntity -> enderecoEntity.getIdEndereco() == enderecoDTO.getIdEndereco())) {
            throw new EnderecoFrontofficeException("Endereço não encontrado");
        }

        listaEnderecoEntity.stream().filter(endereco -> endereco.getIdEndereco() == enderecoDTO.getIdEndereco()).findFirst().get().setPadrao(true);

        listaEnderecoEntity.stream().filter(enderecoEntity -> enderecoEntity.getIdEndereco() != enderecoDTO.getIdEndereco()).forEach(enderecoEntity -> {
            enderecoEntity.setPadrao(false);
        });

        enderecoRepository.saveAll(listaEnderecoEntity);
    }
}