package com.conexao_digital.frontoffice.enums;

public enum StatusPedidoEnum {
    AGUARDANDO_PAGAMENTO(1),
    PAGAMENTO_REJEITADO(2),
    PAGAMENTO_SUCESSO(3),
    AGUARDANDO_RETIRADA(4),
    EM_TRANSITO(5),
    ENTREGUE(6);

    private final int id;

    StatusPedidoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static StatusPedidoEnum fromId(int id) {
        for (StatusPedidoEnum grupo : values()) {
            if (grupo.getId() == id) {
                return grupo;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}

