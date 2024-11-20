package com.conexao_digital.backoffice.utils;

import org.springframework.security.core.userdetails.UserDetails;
import com.conexao_digital.backoffice.enums.UsuarioGrupoEnum;

public class AutenticacaoUtils {
    public static UsuarioGrupoEnum retornarUsuarioGrupo(UserDetails usuario) {
        String grupo = usuario.getAuthorities().stream().findFirst().get().getAuthority();

        switch (grupo) {
            case "ROLE_ADMIN":
                return UsuarioGrupoEnum.ADMIN;
            default:
                return UsuarioGrupoEnum.ESTOQUISTA;
        }
    }
}
