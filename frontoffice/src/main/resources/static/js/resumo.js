function finalizarCompra() {
    fecharToast();

    $.post("/pedido/finalizar", function(data) {
        if (data) {
            if (data.status !== "OK") {
                abrirToastErro(data.mensagem);
            }
            const elModal = document.getElementById('modal-finalizacao-pedido');

            document.getElementById('valor-pedido-modal').innerText = `Valor total: R$ ${Number(data.valortotal).toFixed(2).replace('.', ',')}`;
            document.getElementById('numero-pedido-modal').innerText = `Número do pedido: # ${data.numeropedido}`;

            const modal = new bootstrap.Modal(elModal)

            elModal.addEventListener('hidden.bs.modal', event => {
                window.location.href = '/pedido/meuspedidos';
            })

            modal.show()
        } else {
            abrirToastErro("Erro ao finalizar pedido.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}