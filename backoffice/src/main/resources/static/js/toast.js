function abrirToastErro(mensagem) {
    var toastEl = document.getElementById('toast');
    var toast = new bootstrap.Toast(toastEl);
    document.getElementById("mensagem-toast").innerHTML = mensagem;
    toast.show();
}

function fecharToast() {
    var toastEl = document.getElementById('toast');
    var toast = new bootstrap.Toast(toastEl);
    toast.hide();
}