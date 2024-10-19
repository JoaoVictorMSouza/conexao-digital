package com.conexao_digital.frontoffice.enums;

public enum FormaPagamentoEnum {
    BOLETO(1),
    CARTAOCREDITO(2);

    private final int id;

    FormaPagamentoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static FormaPagamentoEnum fromId(int id) {
        for (FormaPagamentoEnum formaPagamento : values()) {
            if (formaPagamento.getId() == id) {
                return formaPagamento;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
