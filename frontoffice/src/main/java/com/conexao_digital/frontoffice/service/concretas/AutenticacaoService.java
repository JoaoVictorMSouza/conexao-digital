package com.conexao_digital.frontoffice.service.concretas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.conexao_digital.frontoffice.dto.UsuarioLogadoDTO;
import com.conexao_digital.frontoffice.entity.frontoffice.UsuarioFrontofficeEntity;
import com.conexao_digital.frontoffice.enums.GeneroEnum;
import com.conexao_digital.frontoffice.repository.interfaces.frontoffice.IUsuarioFrontofficeRepository;
import com.conexao_digital.frontoffice.service.interfaces.IAutenticacaoService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Service
public class AutenticacaoService implements IAutenticacaoService {
    private IUsuarioFrontofficeRepository usuarioRepository;

    @Autowired
    public AutenticacaoService(IUsuarioFrontofficeRepository iUsuarioRepository) {
        this.usuarioRepository = iUsuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioFrontofficeEntity usuario = this.usuarioRepository.findByDsEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email);
        }

        return User.builder()
                .username(usuario.getDsEmail())
                .password(usuario.getDsSenha())
                .build();
    }

    public UsuarioLogadoDTO retornarUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails usuario = (UserDetails) authentication.getPrincipal();
            UsuarioFrontofficeEntity usuarioFrontofficeEntity = this.usuarioRepository.findByDsEmail(usuario.getUsername());
            return new UsuarioLogadoDTO(usuario, usuarioFrontofficeEntity);
        }
        return null;
    }
}