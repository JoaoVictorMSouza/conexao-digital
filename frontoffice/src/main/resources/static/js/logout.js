document.getElementById('logoutButton').addEventListener('click', function() {
    // Função para deslogar o usuário
    function logout() {
        // Aqui você pode adicionar a lógica para deslogar o usuário, por exemplo, removendo tokens de autenticação
        // ou fazendo uma requisição para o backend para finalizar a sessão do usuário.
        
        // Redirecionar para a página inicial
        window.location.href = '/carrinho';
    }

    logout();
});