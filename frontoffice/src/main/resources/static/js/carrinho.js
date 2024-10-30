$(document).ready(function(){
    $('#cep').mask('00000-000');
});

function diminuirQtdProduto(element) {
    let idProduto = element.dataset.idproduto;
    let elQtdProduto = document.getElementById('quantidade-' + idProduto);
    let qtdProduto = parseInt(elQtdProduto.value, 10);
    let newQtdProduto = qtdProduto - 1;
    if (newQtdProduto < 1) {
        removerProduto(idProduto);
        return;
    }
    let url = "/carrinho/atualizarQuantidade/" + idProduto + "/" + newQtdProduto;
    $.post(url, function(data) {
        if (data) {
            window.location.href = "/carrinho";
        } else {
            abrirToastErro("Erro alterar a quantidade de produtos.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}
function removerProduto(idProduto) {
    let url = "/carrinho/remover/" + idProduto;
    $.post(url, function(data) {
        if (data) {
            window.location.href = "/carrinho";
        } else {
            abrirToastErro("Erro ao remover o produto.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}

function aumentarQtdProduto(element) {
    let idProduto = element.dataset.idproduto;

    let elQtdProduto = document.getElementById('quantidade-' + idProduto);
    let qtdProduto = parseInt(elQtdProduto.value, 10);
    let newQtdProduto = qtdProduto + 1;

    let url = "/carrinho/atualizarQuantidade/" + idProduto + "/" + newQtdProduto;

    $.post(url, function(data) {
        if (data) {
            window.location.href = "/carrinho";
        } else {
            abrirToastErro("Erro alterar a quantidade de produtos.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}

function calcularFrete() {
    const cep = document.getElementById('cep').value;
    if (cep.length !== 9) {
        abrirToastErro("CEP inválido.");
        return;
    }
    const frete = document.getElementById('frete').value;
    let valorFrete;

    switch(frete) {
        case 'normal':
            valorFrete = 10.00;
            break;
        case 'rapido':
            valorFrete = 20.00;
            break;
        case 'ultra-rapido':
            valorFrete = 30.00;
            break;
        default:
            valorFrete = 0.00;
    }

    const valorTotalItens = parseFloat(document.getElementById('valor-total-itens').innerText.replace('R$', '').replace(',', '.'));
    const valorTotalPedido = valorTotalItens + valorFrete;

    document.getElementById('valor-frete').innerText = `R$ ${valorFrete.toFixed(2).replace('.', ',')}`;
    document.getElementById('valor-total-pedido').innerText = `R$ ${valorTotalPedido.toFixed(2).replace('.', ',')}`;
    document.getElementById('resultado-frete').innerText = `O valor do frete (${frete}) para o CEP ${cep} é R$ ${valorFrete.toFixed(2).replace('.', ',')}`;

    let carrinho = {
        valorFrete: valorFrete,
        cep: cep
    }

    $.post("/carrinho/calcularFrete", carrinho, function(data) {
        if (data) {
            if (data.status !== "OK") {
                abrirToastErro(data.mensagem);
            }
            document.getElementById('btn-checkout').style.display = 'block';
        } else {
            abrirToastErro("Erro ao calcular frete.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}

function irParaCheckout(urlRedirect) {
    const cep = document.getElementById('cep').value;
    if (cep.length !== 9) {
        abrirToastErro("CEP inválido.");
        return;
    }

    let href = "/pedido/checkout";

    if (urlRedirect) {
        href += "?urlRedirect=" + urlRedirect;
    }

    window.location.href = href;
}