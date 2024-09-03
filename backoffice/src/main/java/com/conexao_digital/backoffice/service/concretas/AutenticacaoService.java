package com.conexao_digital.backoffice.service.concretas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.conexao_digital.backoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.backoffice.entity.UsuarioBackofficeEntity;
import com.conexao_digital.backoffice.enums.UsuarioGrupo;
import com.conexao_digital.backoffice.repository.interfaces.IUsuarioBackOfficeRepository;
import com.conexao_digital.backoffice.service.interfaces.IAutenticacaoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Service
public class AutenticacaoService implements IAutenticacaoService {
    private IUsuarioBackOfficeRepository usuarioRepository;

    @Autowired
    public AutenticacaoService(IUsuarioBackOfficeRepository iUsuarioRepository) {
        this.usuarioRepository = iUsuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioBackofficeEntity usuario = this.usuarioRepository.findByDsEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email);
        }

        UsuarioGrupo usuarioGrupo = UsuarioGrupo.fromId(usuario.getIdGrupo());

        return User.builder()
                .username(usuario.getDsEmail())
                .password(usuario.getDsSenha())
                .disabled(!usuario.isAtivo())
                .roles(usuarioGrupo.name().toUpperCase())
                .build();
    }

    public UsuarioLogadoDTO retornarUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails usuario = (UserDetails) authentication.getPrincipal();
            UsuarioBackofficeEntity usuarioBackofficeEntity = this.usuarioRepository.findByDsEmail(usuario.getUsername());
            return new UsuarioLogadoDTO(usuario, usuarioBackofficeEntity);
        }
        return null;
    }
}