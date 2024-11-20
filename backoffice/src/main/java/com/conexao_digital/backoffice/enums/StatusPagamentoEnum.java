package com.conexao_digital.backoffice.enums;

public enum StatusPagamentoEnum {
    AGUARDANDO_PAGAMENTO(1),
    PAGAMENTO_CONFIRMADO(2),
    PAGAMENTO_CANCELADO(3);

    private final int id;

    StatusPagamentoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static StatusPagamentoEnum fromId(int id) {
        for (StatusPagamentoEnum grupo : values()) {
            if (grupo.getId() == id) {
                return grupo;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
