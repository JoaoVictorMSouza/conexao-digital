$(document).ready(function(){
    $('#cpf').mask('000.000.000-00');
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

$(document).ready(function () {
    $('#formulario-editar-usuario').submit(async function(e) {
        e.preventDefault();
        let isInformacoesValidas = await validarInformacoesEditarUsuario();
        if (isInformacoesValidas) {
            editarUsuarioFrontoffice();
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

async function validarInformacoesEditarUsuario(){
    try {
        fecharToast();

        let nome = document.getElementById("nome").value;
        let senha = document.getElementById("senha").value;
        let confirmacaoSenha = document.getElementById("confirmacaoSenha").value;
        let genero = document.getElementById("genero-usuario").value;
        let dataNascimento = document.getElementById("data-nascimento").value;
    
        if (nome === ""){
            abrirToastErro("Nome é obrigatório.");
            return false;
        }
    
        if ((senha !== "" || confirmacaoSenha !== "") && !validarSenha()){
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

        if (!validarNome()) {
            return false;
        }
    
        return true;
    } catch (error) {
        return false;
    }
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

function editarUsuarioFrontoffice() {
    let usuario = {
        id: $("#id-usuario").val(),
        nome: $("#nome").val(),
        senha: $("#senha").val(),
        confirmacaoSenha: $("#confirmacaoSenha").val(),
        idGeneroUsuario: $("#genero-usuario").val(),
        dataNascimento: stringToDate($("#data-nascimento").val())
    }

    $.post("/usuario/editar", usuario, function(data) {
        if (data) {
            if (data.status === "OK") {
                window.location.href = "/usuario/editar/" + usuario.id;
            } else {
                abrirToastErro(data.mensagem);
            }
        } else {
            abrirToastErro("Erro ao editar usuário.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
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