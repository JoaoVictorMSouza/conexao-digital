package com.conexao_digital.backoffice.enums;

public enum UsuarioGrupoEnum {
    ADMIN(1),
    ESTOQUISTA(2);

    private final int id;

    UsuarioGrupoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UsuarioGrupoEnum fromId(int id) {
        for (UsuarioGrupoEnum grupo : values()) {
            if (grupo.getId() == id) {
                return grupo;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
