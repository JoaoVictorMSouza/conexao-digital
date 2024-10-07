$(document).ready(function() {
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        abrirToastErro('Usuário ou senha incorretos');
    }
});