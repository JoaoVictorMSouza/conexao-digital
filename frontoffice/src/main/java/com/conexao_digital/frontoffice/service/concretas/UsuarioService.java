package com.conexao_digital.frontoffice.service.concretas;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.conexao_digital.frontoffice.dto.UsuarioFrontofficeDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.EnderecoEntity;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;
import com.conexao_digital.frontoffice.exception.UsuarioFrontofficeException;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IUsuarioFrontofficeRepository;
import com.conexao_digital.frontoffice.service.interfaces.IUsuarioService;
import com.conexao_digital.frontoffice.utils.CpfUtils;

@Service
public class UsuarioService implements IUsuarioService {
    private IUsuarioFrontofficeRepository usuarioRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder senhaEncoder;

    @Autowired
    public UsuarioService(IUsuarioFrontofficeRepository iUsuarioRepository, ModelMapper modelMapper, PasswordEncoder senhaEncoder) {
        this.usuarioRepository = iUsuarioRepository;
        this.modelMapper = modelMapper;
        this.senhaEncoder = senhaEncoder;
    }

    public void criarUsuarioFrontOffice(UsuarioFrontofficeDTO usuarioFrontofficeDTO) {
        usuarioFrontofficeDTO.setGeneroEnum();

        this.validarNome(usuarioFrontofficeDTO.getNome());

        this.validarSenha(usuarioFrontofficeDTO.getSenha(), usuarioFrontofficeDTO.getConfirmacaoSenha());

        if (this.verificarExistenciaEmail(usuarioFrontofficeDTO.getEmail())) {
            throw new UsuarioFrontofficeException("E-mail já cadastrado");
        }

        if (!CpfUtils.validarCPF(usuarioFrontofficeDTO.getCpf())) {
            throw new UsuarioFrontofficeException("CPF inválido");
        }

        if (usuarioFrontofficeDTO.getDataNascimento().after(new Date())) {
            throw new UsuarioFrontofficeException("Data de nascimento inválida");
        }

        // Encripta a senha
        String senhaEncriptada = senhaEncoder.encode(usuarioFrontofficeDTO.getSenha());
        usuarioFrontofficeDTO.setSenha(senhaEncriptada);

        // Mapeia o DTO para a entidade
        UsuarioFrontofficeEntity usuarioFrontofficeEntity = this.mapearUsuarioFrontofficeDTOParaUsuarioFrontofficeEntity(usuarioFrontofficeDTO);

        for (EnderecoEntity endereco : usuarioFrontofficeEntity.getEnderecos()) {
           endereco.setUsuario(usuarioFrontofficeEntity);
        }

        UsuarioFrontofficeEntity usuarioCadastrado = usuarioRepository.save(usuarioFrontofficeEntity);
    }

    private UsuarioFrontofficeDTO mapearUsuarioFrontofficeEntityParaUsuarioFrontofficeDTO(UsuarioFrontofficeEntity usuarioFrontofficeEntity) {
        return modelMapper.map(usuarioFrontofficeEntity, UsuarioFrontofficeDTO.class);
    }

    private UsuarioFrontofficeEntity mapearUsuarioFrontofficeDTOParaUsuarioFrontofficeEntity(UsuarioFrontofficeDTO usuarioFrontofficeDTO) {
        return modelMapper.map(usuarioFrontofficeDTO, UsuarioFrontofficeEntity.class);
    }

    private void validarSenha(String senha, String confirmacaoSenha) {
        if (!senha.equals(confirmacaoSenha)) {
            throw new UsuarioFrontofficeException("As senhas não conferem");
        }
    }

    private void validarNome(String nome) {
        String[] palavrasNomeUsuario = nome.split(" ");

        if (palavrasNomeUsuario.length < 2) {
            throw new UsuarioFrontofficeException("Nome inválido");
        }

        for (String palavra : palavrasNomeUsuario) {
            if (palavra.length() < 3) {
                throw new UsuarioFrontofficeException("Nome inválido");
                
            }
        }
    }

    public boolean verificarExistenciaEmail(String email) {
        UsuarioFrontofficeEntity usuarioFrontofficeEntity = usuarioRepository.findByDsEmail(email);

        if (usuarioFrontofficeEntity != null) {
            return true;
        };

        return false;
    }

    public UsuarioFrontofficeDTO buscarUsuarioFrontOfficePorId(int id) {
        UsuarioFrontofficeEntity usuarioFrontofficeEntity = usuarioRepository.findByIdUsuario(id);

        if (usuarioFrontofficeEntity == null) {
            throw new UsuarioFrontofficeException("Usuário não encontrado");
        }

        return this.mapearUsuarioFrontofficeEntityParaUsuarioFrontofficeDTO(usuarioFrontofficeEntity);
    }

    public void editarUsuarioFrontOffice(UsuarioFrontofficeDTO usuarioFrontofficeDTO){
        UsuarioFrontofficeEntity usuarioFrontofficeEntity = usuarioRepository.findByIdUsuario(usuarioFrontofficeDTO.getId());

        if (usuarioFrontofficeEntity == null) {
            throw new UsuarioFrontofficeException("Usuário não encontrado");
        }

        if ((usuarioFrontofficeDTO.getSenha() != null && !usuarioFrontofficeDTO.getSenha().trim().isEmpty()) || 
            (usuarioFrontofficeDTO.getConfirmacaoSenha() != null && !usuarioFrontofficeDTO.getConfirmacaoSenha().trim().isEmpty())) {

            this.validarSenha(usuarioFrontofficeDTO.getSenha(), usuarioFrontofficeDTO.getConfirmacaoSenha());

            // Encripta a senha
            String senhaEncriptada = senhaEncoder.encode(usuarioFrontofficeDTO.getSenha());
            usuarioFrontofficeDTO.setSenha(senhaEncriptada);
            usuarioFrontofficeEntity.setDsSenha(usuarioFrontofficeDTO.getSenha());
        }

        if (usuarioFrontofficeDTO.getNome() != null && 
            !usuarioFrontofficeDTO.getNome().trim().isEmpty() && 
            !usuarioFrontofficeEntity.getDsNome().equals(usuarioFrontofficeDTO.getNome())) {

            this.validarNome(usuarioFrontofficeDTO.getNome());
            usuarioFrontofficeEntity.setDsNome(usuarioFrontofficeDTO.getNome()); 
        }

        if (usuarioFrontofficeDTO.getIdGeneroUsuario() > 0 && 
            usuarioFrontofficeDTO.getIdGeneroUsuario() != usuarioFrontofficeEntity.getIdGenero()) {

            usuarioFrontofficeEntity.setIdGenero(usuarioFrontofficeDTO.getIdGeneroUsuario());     
        }

        if (usuarioFrontofficeDTO.getDataNascimento() != null && 
            !usuarioFrontofficeDTO.getDataNascimento().equals(usuarioFrontofficeEntity.getDtNascimento())) {

            usuarioFrontofficeEntity.setDtNascimento(usuarioFrontofficeDTO.getDataNascimento());     
    }

        usuarioRepository.save(usuarioFrontofficeEntity);
    }
}