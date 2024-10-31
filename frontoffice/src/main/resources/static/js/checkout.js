$(document).ready(function(){
    $('#credit-card-number').mask('0000 0000 0000 0000');
    $('#credit-card-expiry').mask('00/00');
    $('#credit-card-cvv').mask('0000'); // Assuming CVV can be up to 4 digits
});

function pagamentoBoleto() {
    document.getElementById('boleto-container').style.backgroundColor = '#8bf793';
    document.getElementById('boleto').style.display = 'block';

    document.getElementById('cartao-credito-container').style.backgroundColor = '#f8f9fa';
    document.getElementById('cartao-credito').style.display = 'none';

    document.getElementById('finalizar-compra').style.display = 'block';

    let url = "/carrinho/selecionarFormaPagamento/1"; // 1 = Boleto

    document.getElementById('forma-pagamento-escolhida').value = 1;

    $.post(url, function(data) {
        if (data) {
            if (data.status !== "OK") {
                abrirToastErro(data.mensagem);
            }
        } else {
            abrirToastErro("Erro ao selecionar forma de pagamento");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}

function pagamentoCartaoCredito() {
    document.getElementById('boleto-container').style.backgroundColor = '#f8f9fa';
    document.getElementById('boleto').style.display = 'none';

    document.getElementById('cartao-credito-container').style.backgroundColor = '#8bf793';
    document.getElementById('cartao-credito').style.display = 'block';

    document.getElementById('finalizar-compra').style.display = 'block';

    let url = "/carrinho/selecionarFormaPagamento/2"; // 2 = Cartão de crédito

    document.getElementById('forma-pagamento-escolhida').value = 2;

    $.post(url, function(data) {
        if (data) {
            if (data.status !== "OK") {
                abrirToastErro(data.mensagem);
            }
        } else {
            abrirToastErro("Erro ao selecionar forma de pagamento");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}

function validarCheckout() {
    fecharToast();

    let formaPagamento = document.getElementById('forma-pagamento-escolhida').value;

    if (formaPagamento === "2") {
        let numeroCartao = document.getElementById('credit-card-number').value;
        let dataExpiracao = document.getElementById('credit-card-expiry').value;
        let cvv = document.getElementById('credit-card-cvv').value;
        let parcelas = document.getElementById('parcelas').value;

        if (numeroCartao === "" || dataExpiracao === "" || cvv === "" || isNaN(Number(parcelas))) {
            abrirToastErro("Preencha todos os campos do cartão de crédito");
            return false;
        }
    }

    if (formaPagamento === "1") {
        if (!document.getElementById('termos-boleto').checked) {
            abrirToastErro("Por favor, concorde com os termos para continuar");
            return false;
        }
    }

    window.location.href = `/pedido/resumo`;
}