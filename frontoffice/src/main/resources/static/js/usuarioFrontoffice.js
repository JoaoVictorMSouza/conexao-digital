$(document).ready(function(){
    $('#cpf').mask('000.000.000-00');
});

$(document).ready(function(){
    $('.cep').mask('00000-000');
});

$(document).ready(function () {
    $('#formulario-criar-usuario').submit(async function(e) {
        e.preventDefault();
        let isInformacoesValidas = await validarInformacoesCriarUsuario();
        if (isInformacoesValidas) {
            criarUsuarioFrontoffice();
        }
    });
});

const emailEl = document.getElementById("email");

async function validarInformacoesCriarUsuario(){
    try {
        fecharToast();

        let nome = document.getElementById("nome").value;
        let cpf = document.getElementById("cpf").value;
        let email = document.getElementById("email").value;
        let senha = document.getElementById("senha").value;
        let confirmacaoSenha = document.getElementById("confirmacaoSenha").value;
        let genero = document.getElementById("genero-usuario").value;
        let dataNascimento = document.getElementById("data-nascimento").value;
    
        if (nome === ""){
            abrirToastErro("Nome é obrigatório.");
            return false;
        }
        if (cpf === "" ){
            abrirToastErro("CPF é obrigatório.");
            return false;
        }

        if (!validarCPF(cpf)) {
            return false;
        }
    
        if (email === ""){
            abrirToastErro("Email é obrigatório.");
            return false;
        }

        let isEmailValido = await validarEmail();
        if (!isEmailValido) {
            return false;
        }
    
        if (senha === ""){
            abrirToastErro("Senha é obrigatória.");
            return false;
        }
    
        if (confirmacaoSenha === ""){
            abrirToastErro("Confirmação de senha é obrigatória.");
            return false;
        }

        if (!validarSenha()) {
            return false;
        }
    
        if (genero === "" || Number(genero) <= 0){
            abrirToastErro("Gênero é obrigatório.");
            return false;
        }

        if (dataNascimento === ""){
            abrirToastErro("Data de nascimento é obrigatória.");
            return false;
        }

        if (stringToDate(dataNascimento) > new Date()) {
            abrirToastErro("Data de nascimento inválida.");
            return false;
        }

        if (!validarEnderecoFaturamento()) {
            return false;
        }

        if (!validarEnderecoEntrega()) {
            return false;
        }

        if (!validarNome()) {
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

function validarCPF(numeroCpf) {
    const cpf = numeroCpf.replace(/[^\d]+/g, '');
    if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) {
        abrirToastErro('CPF inválido');
        return false;
    }

    let soma = 0;
    let resto;

    for (let i = 1; i <= 9; i++) {
        soma += parseInt(cpf.substring(i - 1, i)) * (11 - i);
    }
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.substring(9, 10))) {
        abrirToastErro('CPF inválido');
        return false;
    }

    soma = 0;
    for (let i = 1; i <= 10; i++) {
        soma += parseInt(cpf.substring(i - 1, i)) * (12 - i);
    }
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.substring(10, 11))) {
        abrirToastErro('CPF inválido');
        return false;
    }

    return true;
}

function validarSenha() {
    let senha = document.getElementById("senha").value;
    let confirmacaoSenha = document.getElementById("confirmacaoSenha").value;
    if (senha !== confirmacaoSenha) {
        abrirToastErro("As senhas não coincidem.");
        return false;
    }
    return true;
}

async function validarEmail() {
    const email = document.getElementById("email").value;
    return new Promise((resolve, reject) => {
        $.get(`/usuario/consultarEmail?email=${encodeURIComponent(email)}`, function(data) {
            if (data.emailExiste) {
                abrirToastErro("Email já cadastrado!");
                resolve(false);
            } else {
                resolve(true);
            }
        }).fail(function() {
            reject(new Error("Erro ao consultar o email"));
        });
    });
}

function criarUsuarioFrontoffice() {
    let enderecoFaturamento = {
        cep: $("#cep-faturamento").val(),
        logradouro: $("#logradouro-faturamento").val(),
        numero: $("#numero-faturamento").val(),
        complemento: $("#complemento-faturamento").val(),
        bairro: $("#bairro-faturamento").val(),
        cidade: $("#cidade-faturamento").val(),
        uf: $("#uf-faturamento").val(),
        idTipoEndereco: $("#tipo-endereco-faturamento").val(),
        padrao: false
    }

    let enderecoEntrega = {
        cep: $("#cep-entrega").val(),
        logradouro: $("#logradouro-entrega").val(),
        numero: $("#numero-entrega").val(),
        complemento: $("#complemento-entrega").val(),
        bairro: $("#bairro-entrega").val(),
        cidade: $("#cidade-entrega").val(),
        uf: $("#uf-entrega").val(),
        idTipoEndereco: $("#tipo-endereco-entrega").val(),
        padrao: true
    }

    let usuario = {
        nome: $("#nome").val(),
        cpf: $("#cpf").val(),
        email: $("#email").val(),
        senha: $("#senha").val(),
        confirmacaoSenha: $("#confirmacaoSenha").val(),
        idGeneroUsuario: $("#genero-usuario").val(),
        dataNascimento: stringToDate($("#data-nascimento").val()),
        enderecos: [enderecoFaturamento, enderecoEntrega]
    }

    $.ajax({
        url: "/usuario/criar",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(usuario),
        success: function(data) {
            if (data) {
                if (data.status === "OK") {
                    window.location.href = "/login";
                } else {
                    abrirToastErro(data.mensagem);
                }
            } else {
                abrirToastErro("Erro ao inserir usuário.");
            }
        },
        error: function(data) {
            if (data.responseText) {
                abrirToastErro(data.responseText);
            }
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

async function inserirMesmoEndereco(element) {
    if (!element.checked) {
        habilitarCamposEnderecoEntrega();
    }

    let cep = document.getElementById("cep-faturamento").value;
    if (cep.length !== 9) {
        abrirToastErro("CEP de faturamento inválido.");
        element.checked = false;
        return;
    }

    if (element.checked) {
        let endereco = await consultarCep(cep);
        if (endereco) {
            document.getElementById("cep-entrega").value = document.getElementById("cep-faturamento").value;
            document.getElementById("cep-entrega").disabled = true;

            document.getElementById("logradouro-entrega").value = endereco.logradouro;

            document.getElementById("bairro-entrega").value = endereco.bairro;

            document.getElementById("cidade-entrega").value = endereco.localidade;

            document.getElementById("uf-entrega").value = endereco.uf;

            document.getElementById("complemento-entrega").value = document.getElementById("complemento-faturamento").value;
            document.getElementById("complemento-entrega").disabled = true;

            document.getElementById("numero-entrega").value = document.getElementById("numero-faturamento").value;
            document.getElementById("numero-entrega").disabled = true;

            document.getElementById("btn-buscar-frete-entrega").disabled = true;

        }
    }
}

function habilitarCamposEnderecoEntrega() {
    document.getElementById("cep-entrega").disabled = false;
    document.getElementById("complemento-entrega").disabled = false;
    document.getElementById("numero-entrega").disabled = false;
    document.getElementById("btn-buscar-frete-entrega").disabled = false;
}

function validarNome() {
    let nome = document.getElementById('nome').value;
    let palavras = nome.trim().split(' ');

    if (palavras.length < 2 || palavras.some(p => p.length < 3)) {
        abrirToastErro('O nome deve conter pelo menos duas palavras, com no mínimo três letras em cada palavra.');
        return false;
    }
    return true;
}

function stringToDate(dateString) {
    // Split the string into components
    const parts = dateString.split('-');
    // Create a new Date object (month is zero-indexed, so subtract 1)
    const date = new Date(parts[0], parts[1] - 1, parts[2]);
    return date;
}