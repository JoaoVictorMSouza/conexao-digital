package com.conexao_digital.frontoffice.enums;

public enum EnderecoTipoEnum {
    ENTREGA(1),
    FATURAMENTO(2);

    private final int id;

    EnderecoTipoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EnderecoTipoEnum fromId(int id) {
        for (EnderecoTipoEnum grupo : values()) {
            if (grupo.getId() == id) {
                return grupo;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
