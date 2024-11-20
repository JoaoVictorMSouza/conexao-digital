$(document).ready(function () {
    $('#formulario-editar-pedido').submit(async function(e) {
        e.preventDefault();
        let isInformacoesValidas = await validarInformacoesEditarPedido();
        if (isInformacoesValidas) {
            editarPedidoFrontoffice();
        }
    });
});

function validarInformacoesEditarPedido(){
    try {
        fecharToast();

        let idPedido = document.getElementById("id-pedido").value;
        let statusPedido = document.getElementById("status-pedido").value;

        if (idPedido === "" || Number(idPedido) <= 0){
            abrirToastErro("ID do pedido é obrigatório.");
            return false;
        }
    
        if (statusPedido === "" || Number(statusPedido) <= 0){
            abrirToastErro("Status do pedido é obrigatório.");
            return false;
        }
    
        return true;
    } catch (error) {
        return false;
    }
}

function editarPedidoFrontoffice() {
    let pedido = {
        idPedido: $("#id-pedido").val(),
        idStatusPedidoEnum: $("#status-pedido").val()
    }

    $.post("/pedido/editarpedido", pedido, function(data) {
        if (data) {
            if (data.status === "OK") {
                window.location.href = "/pedido/listarPedidosFrontoffice";
            } else {
                abrirToastErro(data.mensagem);
            }
        } else {
            abrirToastErro("Erro ao editar pedido.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}