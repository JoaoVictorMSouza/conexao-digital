$(document).ready(function(){
    $('#cep').mask('00000-000');
});

$(document).ready(function () {
    $('#form-calculo-frete').submit(async function(e) {
        //REGRA DE CALCULO DE FRETE
    });
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