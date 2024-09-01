package com.conexao_digital.backoffice.enums;

public enum UsuarioGrupo {
    ADMIN(1),
    ESTOQUISTA(2);

    private final int id;

    UsuarioGrupo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UsuarioGrupo fromId(int id) {
        for (UsuarioGrupo grupo : values()) {
            if (grupo.getId() == id) {
                return grupo;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
