package com.conexao_digital.backoffice.utils;

import org.springframework.security.core.userdetails.UserDetails;
import com.conexao_digital.backoffice.enums.UsuarioGrupo;

public class AutenticacaoUtils {
    public static UsuarioGrupo retornarUsuarioGrupo(UserDetails usuario) {
        String grupo = usuario.getAuthorities().stream().findFirst().get().getAuthority();

        switch (grupo) {
            case "ROLE_ADMIN":
                return UsuarioGrupo.ADMIN;
            default:
                return UsuarioGrupo.ESTOQUISTA;
        }
    }
}
