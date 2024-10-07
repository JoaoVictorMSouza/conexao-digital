package com.conexao_digital.frontoffice.enums;

public enum GeneroEnum {
    MASCULINO(1),
    FEMININO(2),
    OUTRO(3);

    private final int id;

    GeneroEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GeneroEnum fromId(int id) {
        for (GeneroEnum grupo : values()) {
            if (grupo.getId() == id) {
                return grupo;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
