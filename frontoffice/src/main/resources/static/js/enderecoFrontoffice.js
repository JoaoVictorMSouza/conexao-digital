$(document).ready(function(){
    $('.cep').mask('00000-000');
});

$(document).ready(function () {
    $('#formulario-criar-endereco').submit(async function(e) {
        e.preventDefault();
        let isInformacoesValidas = await validarInformacoesCriarEndereco();
        if (isInformacoesValidas) {
            criarEnderecoFrontoffice();
        }
    });
});

async function definirEnderecoPadrao(element) {
    if (!element.checked) {
        element.checked = !element.checked;
        return false;
    }

    let idEndereco = element.dataset.idendereco;
    let idUsuario = $("#id-usuario").val();
    let endereco = {
        idEndereco: idEndereco,
        padrao: true,
        idUsuario: idUsuario
    }

    $.post("/endereco/editarEnderecoPadrao", endereco, function(data) {
        if (data) {
            if (data.status === "OK") {
                window.location.href = "/usuario/editar/" + endereco.idUsuario;
            } else {
                abrirToastErro(data.mensagem);
            }
        } else {
            abrirToastErro("Erro ao definir endereço padrão.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}

async function validarInformacoesCriarEndereco(){
    try {
        fecharToast();

        if (!validarEnderecoEntrega()) {
            return false;
        }
    
        return true;
    } catch (error) {
        return false;
    }
}

function validarEnderecoFaturamento() {
    let cepFaturamento = document.getElementById("cep-faturamento").value;
    let logradouroFaturamento = document.getElementById("logradouro-faturamento").value;
    let numeroFaturamento = document.getElementById("numero-faturamento").value;
    let bairroFaturamento = document.getElementById("bairro-faturamento").value;
    let cidadeFaturamento = document.getElementById("cidade-faturamento").value;
    let ufFaturamento = document.getElementById("uf-faturamento").value;

    if (cepFaturamento === "") {
        abrirToastErro("CEP de faturamento é obrigatório.");
        return false;
    }

    if (logradouroFaturamento === "") {
        abrirToastErro("Logradouro de faturamento é obrigatório.");
        return false;
    }

    if (numeroFaturamento === "") {
        abrirToastErro("Número de faturamento é obrigatório.");
        return false;
    }

    if (bairroFaturamento === "") {
        abrirToastErro("Bairro de faturamento é obrigatório.");
        return false;
    }

    if (cidadeFaturamento === "") {
        abrirToastErro("Cidade de faturamento é obrigatório.");
        return false;
    }

    if (ufFaturamento === "") {
        abrirToastErro("UF de faturamento é obrigatório.");
        return false;
    }

    return true;
}

function validarEnderecoEntrega() {
    let cepEntrega = document.getElementById("cep-entrega").value;
    let logradouroEntrega = document.getElementById("logradouro-entrega").value;
    let numeroEntrega = document.getElementById("numero-entrega").value;
    let bairroEntrega = document.getElementById("bairro-entrega").value;
    let cidadeEntrega = document.getElementById("cidade-entrega").value;
    let ufEntrega = document.getElementById("uf-entrega").value;

    if (cepEntrega === "") {
        abrirToastErro("CEP de entrega é obrigatório.");
        return false;
    }

    if (logradouroEntrega === "") {
        abrirToastErro("Logradouro de entrega é obrigatório.");
        return false;
    }

    if (numeroEntrega === "") {
        abrirToastErro("Número de entrega é obrigatório.");
        return false;
    }

    if (bairroEntrega === "") {
        abrirToastErro("Bairro de entrega é obrigatório.");
        return false;
    }

    if (cidadeEntrega === "") {
        abrirToastErro("Cidade de entrega é obrigatório.");
        return false;
    }

    if (ufEntrega === "") {
        abrirToastErro("UF de entrega é obrigatório.");
        return false;
    }

    return true;
}

function criarEnderecoFrontoffice() {
    let endereco = {
        cep: $("#cep-entrega").val(),
        logradouro: $("#logradouro-entrega").val(),
        numero: $("#numero-entrega").val(),
        complemento: $("#complemento-entrega").val(),
        bairro: $("#bairro-entrega").val(),
        cidade: $("#cidade-entrega").val(),
        uf: $("#uf-entrega").val(),
        idTipoEndereco: $("#tipo-endereco-entrega").val(),
        padrao: false,
        idUsuario: $("#id-usuario").val()
    }

    $.post("/endereco/criar", endereco, function(data) {
        if (data) {
            if (data.status === "OK") {
                window.location.href = "/usuario/editar/" + endereco.idUsuario;
            } else {
                abrirToastErro(data.mensagem);
            }
        } else {
            abrirToastErro("Erro ao inserir endereço.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}

async function consultarCepEntrega() {
    let cep = document.getElementById("cep-entrega").value;
    if (cep.length === 9) {
        let endereco = await consultarCep(cep);
        if (endereco) {
            document.getElementById("logradouro-entrega").value = endereco.logradouro;
            document.getElementById("bairro-entrega").value = endereco.bairro;
            document.getElementById("cidade-entrega").value = endereco.localidade;
            document.getElementById("uf-entrega").value = endereco.uf;
        }
    }
}

async function consultarCepFaturamento() {
    let cep = document.getElementById("cep-faturamento").value;
    if (cep.length === 9) {
        let endereco = await consultarCep(cep);
        if (endereco) {
            document.getElementById("logradouro-faturamento").value = endereco.logradouro;
            document.getElementById("bairro-faturamento").value = endereco.bairro;
            document.getElementById("cidade-faturamento").value = endereco.localidade;
            document.getElementById("uf-faturamento").value = endereco.uf;
        }
    }
}

function consultarCep(cep) {
    return fetch(`https://viacep.com.br/ws/${cep}/json/`)
        .then(response => response.json())
        .then(data => {
            if (data.erro) {
                abrirToastErro('CEP não encontrado.');
                return undefined;
            }
            return data;
        })
        .catch(error => {
            abrirToastErro('Erro ao consultar o CEP.');
            return undefined;
        });
}

function habilitarCamposEnderecoEntrega() {
    document.getElementById("cep-entrega").disabled = false;
    document.getElementById("complemento-entrega").disabled = false;
    document.getElementById("numero-entrega").disabled = false;
    document.getElementById("btn-buscar-frete-entrega").disabled = false;
}