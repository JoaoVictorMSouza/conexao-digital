package com.conexao_digital.backoffice.service.concretas;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.conexao_digital.backoffice.dto.UsuarioBackofficeDTO;
import com.conexao_digital.backoffice.entity.UsuarioBackofficeEntity;
import com.conexao_digital.backoffice.exception.UsuarioBackofficeException;
import com.conexao_digital.backoffice.repository.interfaces.IUsuarioBackOfficeRepository;
import com.conexao_digital.backoffice.service.interfaces.IUsuarioService;
import com.conexao_digital.backoffice.utils.CpfUtils;

@Service
public class UsuarioService implements IUsuarioService {
    private IUsuarioBackOfficeRepository usuarioRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder senhaEncoder;

    @Autowired
    public UsuarioService(IUsuarioBackOfficeRepository iUsuarioRepository, ModelMapper modelMapper, PasswordEncoder senhaEncoder) {
        this.usuarioRepository = iUsuarioRepository;
        this.modelMapper = modelMapper;
        this.senhaEncoder = senhaEncoder;
    }

    public void criarUsuarioBackOffice(UsuarioBackofficeDTO usuarioBackofficeDTO) {
        usuarioBackofficeDTO.setUsuarioGrupo();

        this.validarSenha(usuarioBackofficeDTO.getSenha(), usuarioBackofficeDTO.getConfirmacaoSenha());

        if (this.verificarExistenciaEmail(usuarioBackofficeDTO.getEmail())) {
            throw new UsuarioBackofficeException("E-mail já cadastrado");
        }

        if (!CpfUtils.validarCPF(usuarioBackofficeDTO.getCpf())) {
            throw new UsuarioBackofficeException("CPF inválido");
        }

        // Encripta a senha
        String senhaEncriptada = senhaEncoder.encode(usuarioBackofficeDTO.getSenha());
        usuarioBackofficeDTO.setSenha(senhaEncriptada);

        // Mapeia o DTO para a entidade
        UsuarioBackofficeEntity usuarioBackofficeEntity = this.mapearUsuarioBackofficeDTOParaUsuarioBackofficeEntity(usuarioBackofficeDTO);

        // Sempre cadastra o usuário como ativo
        usuarioBackofficeEntity.setAtivo(true);

        UsuarioBackofficeEntity usuarioCadastrado = usuarioRepository.save(usuarioBackofficeEntity);
    }

    private UsuarioBackofficeDTO mapearUsuarioBackofficeEntityParaUsuarioBackofficeDTO(UsuarioBackofficeEntity usuarioBackofficeEntity) {
        return modelMapper.map(usuarioBackofficeEntity, UsuarioBackofficeDTO.class);
    }

    private UsuarioBackofficeEntity mapearUsuarioBackofficeDTOParaUsuarioBackofficeEntity(UsuarioBackofficeDTO usuarioBackofficeDTO) {
        return modelMapper.map(usuarioBackofficeDTO, UsuarioBackofficeEntity.class);
    }

    private void validarSenha(String senha, String confirmacaoSenha) {
        if (!senha.equals(confirmacaoSenha)) {
            throw new UsuarioBackofficeException("As senhas não conferem");
        }
    }

    public boolean verificarExistenciaEmail(String email) {
        UsuarioBackofficeEntity usuarioBackofficeEntity = usuarioRepository.findByDsEmail(email);

        if (usuarioBackofficeEntity != null) {
            return true;
        };

        return false;
    }

    public List<UsuarioBackofficeDTO> listarUsuariosBackOffice() {
        List<UsuarioBackofficeEntity> usuariosBackofficeEntity = usuarioRepository.findAll();

        List<UsuarioBackofficeDTO> usuariosBackofficeDTO = usuariosBackofficeEntity.stream()
                .map(usuarioBackofficeEntity -> this.mapearUsuarioBackofficeEntityParaUsuarioBackofficeDTO(usuarioBackofficeEntity))
                .toList();

        return usuariosBackofficeDTO;
    }

    public UsuarioBackofficeDTO buscarUsuarioPorId(int id) {
        UsuarioBackofficeEntity usuarioBackofficeEntity = usuarioRepository.findByIdUsuario(id);

        if (usuarioBackofficeEntity == null) {
            throw new UsuarioBackofficeException("Usuário não encontrado");
        }

        return this.mapearUsuarioBackofficeEntityParaUsuarioBackofficeDTO(usuarioBackofficeEntity);
    }

    public void editarUsuarioBackOffice(UsuarioBackofficeDTO usuarioBackofficeDTO){
        UsuarioBackofficeEntity usuarioBackofficeEntity = usuarioRepository.findByIdUsuario(usuarioBackofficeDTO.getId());

        if (usuarioBackofficeEntity == null) {
            throw new UsuarioBackofficeException("Usuário não encontrado");
        }

        if ((usuarioBackofficeDTO.getSenha() != null && !usuarioBackofficeDTO.getSenha().trim().isEmpty()) || 
            (usuarioBackofficeDTO.getConfirmacaoSenha() != null && !usuarioBackofficeDTO.getConfirmacaoSenha().trim().isEmpty())) {
            this.validarSenha(usuarioBackofficeDTO.getSenha(), usuarioBackofficeDTO.getConfirmacaoSenha());

            // Encripta a senha
            String senhaEncriptada = senhaEncoder.encode(usuarioBackofficeDTO.getSenha());
            usuarioBackofficeDTO.setSenha(senhaEncriptada);
            usuarioBackofficeEntity.setDsSenha(usuarioBackofficeDTO.getSenha());
        }

        if (usuarioBackofficeDTO.getNome() != null && !usuarioBackofficeDTO.getNome().trim().isEmpty()) {
            usuarioBackofficeEntity.setDsNome(usuarioBackofficeDTO.getNome()); 
        }

        if (usuarioBackofficeDTO.getCpf() != null && !usuarioBackofficeDTO.getCpf().trim().isEmpty()) {
            if (!CpfUtils.validarCPF(usuarioBackofficeDTO.getCpf())) {
                throw new UsuarioBackofficeException("CPF inválido");
            }

            usuarioBackofficeEntity.setDsCpf(usuarioBackofficeDTO.getCpf());
        }

        if (usuarioBackofficeDTO.getIdUsuarioGrupo() != usuarioBackofficeEntity.getIdGrupo()) {
            usuarioBackofficeEntity.setIdGrupo(usuarioBackofficeDTO.getIdUsuarioGrupo());     
        }

        usuarioRepository.save(usuarioBackofficeEntity);
    }
    public void editarStatusUsuarioBackOffice(UsuarioBackofficeDTO usuarioBackofficeDTO) {
        UsuarioBackofficeEntity usuarioBackofficeEntity = usuarioRepository.findByIdUsuario(usuarioBackofficeDTO.getId());

        if (usuarioBackofficeEntity == null) {
            throw new UsuarioBackofficeException("Usuário não encontrado");
        }

        usuarioBackofficeEntity.setAtivo(usuarioBackofficeDTO.isAtivo());

        usuarioRepository.save(usuarioBackofficeEntity);
    }
}