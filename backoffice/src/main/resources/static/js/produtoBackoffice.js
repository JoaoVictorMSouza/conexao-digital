function filtrarProdutos() {
    const textoFiltro = document.getElementById('campoFiltro').value.toLowerCase();
    const linhasUsuarios = document.querySelectorAll('.linha-usuario');
    
    linhasUsuarios.forEach(linha => {
        const nomeUsuario = linha.querySelector('.nome-produto').textContent.toLowerCase();
        if (nomeUsuario.includes(textoFiltro)) {
            linha.style.display = '';
        } else {
            linha.style.display = 'none';
        }
    });
}