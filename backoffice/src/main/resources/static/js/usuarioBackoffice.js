$(document).ready(function(){
    $('#cpf').mask('000.000.000-00');
});

$(document).ready(function () {
    $('#formulario-criar-usuario').submit(async function(e) {
        e.preventDefault();
        let isInformacoesValidas = await validarInformacoesUsuario();
        if (isInformacoesValidas) {
            criarUsuarioBackOffice();
        }
    });
});

const emailEl = document.getElementById("email");

async function validarInformacoesUsuario(){
    try {
        fecharToast();

        let nome = document.getElementById("nome").value;
        let cpf = document.getElementById("cpf").value;
        let email = document.getElementById("email").value;
        let senha = document.getElementById("senha").value;
        let confirmacaoSenha = document.getElementById("confirmacaoSenha").value;
        let grupo = document.getElementById("grupo-usuario").value;
    
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
    
        if (grupo === "" || Number(grupo) <= 0){
            abrirToastErro("Grupo é obrigatório.");
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

function criarUsuarioBackOffice() {
    let usuario = {
        nome: $("#nome").val(),
        cpf: $("#cpf").val(),
        email: $("#email").val(),
        senha: $("#senha").val(),
        confirmacaoSenha: $("#confirmacaoSenha").val(),
        idUsuarioGrupo: $("#grupo-usuario").val(),
        ativo: $("#ativo").val()
    }

    $.post("/usuario/criar", usuario, function(data) {
        if (data) {
            if (data.status === "OK") {
                window.location.href = "/usuario/listarUsuariosBackOffice";
            } else {
                abrirToastErro(data.mensagem);
            }
        } else {
            abrirToastErro("Erro ao inserir usuário.");
        }
    });
}

function filtrarUsuarios() {
    const textoFiltro = document.getElementById('campoFiltro').value.toLowerCase();
    const linhasUsuarios = document.querySelectorAll('.linha-usuario');
    
    linhasUsuarios.forEach(linha => {
        const nomeUsuario = linha.querySelector('.nome-usuario').textContent.toLowerCase();
        if (nomeUsuario.includes(textoFiltro)) {
            linha.style.display = '';
        } else {
            linha.style.display = 'none';
        }
    });
}