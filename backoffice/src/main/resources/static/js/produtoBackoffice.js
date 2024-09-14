$(document).ready(function () {
    const precoInput = document.getElementById('preco');

    precoInput.addEventListener('input', function () {
        let value = precoInput.value;

        // Remove caracteres não numéricos, exceto o ponto
        value = value.replace(/[^\d.]/g, '');

        // Mantém apenas a primeira ocorrência do ponto
        const parts = value.split('.');
        if (parts.length > 2) {
            value = parts[0] + '.' + parts.slice(1).join('');
        }

        // Formata o número para ter no máximo duas casas decimais
        const number = parseFloat(value);
        if (!isNaN(number)) {
            precoInput.value = number.toFixed(2);
        }
    });
});

$(document).ready(function () {
    $('#formulario-criar-produto').submit(async function(e) {
        e.preventDefault();
        let isInformacoesValidas =  validarInformacoesCriarProduto();
        if (isInformacoesValidas) {
            criarProdutoBackOffice();
        }
    });
});

function validarInformacoesCriarProduto(){
    try {
        fecharToast();

        let nome = $("#nome").val();
        let avaliacao = $("#avaliacao").val();
        let descricao = $("#descricao-detalhada").val();
        let preco = $("#preco").val();
        let quantidade = $("#qtd-estoque").val();
        
        if (nome === ""){
            abrirToastErro("Nome é obrigatório.");
            return false;
        }

        if (nome.length > 200) {
            abrirToastErro("Nome deve ter no máximo 200 caracteres.");
            return false;
        }

        if (avaliacao < 0 || avaliacao > 5){
            abrirToastErro("Avaliação deve ser um valor entre 0 e 5.");
            return false;
        }

        if (descricao === ""){
            abrirToastErro("Descrição é obrigatória.");
            return false;
        }

        if (descricao.length > 2000) {
            abrirToastErro("Descrição deve ter no máximo 2000 caracteres.");
            return false;
        }

        if (preco < 0){
            abrirToastErro("Preço pode ser um valor negativo.");
            return false;
        }

        if (quantidade < 0){
            abrirToastErro("Quantidade em estoque não pode ser um valor negativo.");
            return false;
        }
    
        return true;
    } catch (error) {
        return false;
    }
}

function criarProdutoBackOffice() {
    let formData = new FormData();

    formData.append('nome', $("#nome").val());
    formData.append('avaliacao', $("#avaliacao").val());
    formData.append('descricaoDetalhada', $("#descricao-detalhada").val());
    formData.append('preco', $("#preco").val());
    formData.append('quantidadeEstoque', $("#qtd-estoque").val());
    formData.append('ativo', $("#ativo").val());

    let imagens = document.getElementById('imagens').files;
    for (let i = 0; i < imagens.length; i++) {
        formData.append('imagens', imagens[i]);
    }

    let ordenacaoImagens = $("#ordenacao-imagens").val();
    formData.append('ordenacaoImagens', ordenacaoImagens);

    $.ajax({
        url: '/produto/criar',
        type: 'POST',
        data: formData,
        contentType: false,  // Não defina o contentType, deixe o jQuery manipular isso
        processData: false,  // Evita que o jQuery processe os dados, permitindo o envio do FormData
        success: function(response) {
            if (response) {
                if (response.status === "OK") {
                    window.location.href = "/produto/listarProdutosBackoffice";
                } else {
                    abrirToastErro(response.mensagem);
                }
            } else {
                abrirToastErro("Erro ao inserir produto.");
            }
        },
        error: function(response) {
            if (response.responseText) {
                abrirToastErro(response.responseText);
            }
        }
    });
}

function filtrarProdutos() {
    const textoFiltro = document.getElementById('campoFiltro').value.toLowerCase();
    const linhasUsuarios = document.querySelectorAll('.linha-produto');

    linhasUsuarios.forEach(linha => {
        const nomeUsuario = linha.querySelector('.nome-produto').textContent.toLowerCase();
        if (nomeUsuario.includes(textoFiltro)) {
            linha.style.display = '';
        } else {
            linha.style.display = 'none';
        }
    });
}

function previewImages() {
    var previewContainer = document.getElementById('imagePreviewContainer');
    previewContainer.innerHTML = ""; // Limpa as pré-visualizações anteriores
    var files = document.getElementById('imagens').files;

    filesArray = Array.from(files);  // Armazena arquivos em um array para manipulação

    if (filesArray) {
        filesArray.forEach((file, index) => {
            var reader = new FileReader();

            reader.onload = (function(f, i) {
                return function(e) {
                    var imgWrapper = document.createElement("div");
                    imgWrapper.classList.add("image-wrapper");
                    imgWrapper.setAttribute("data-index", i); // Associa um índice à imagem

                    var imgElement = document.createElement("img");
                    imgElement.src = e.target.result;
                    imgElement.style.width = "150px";  // Define o tamanho da imagem
                    imgElement.style.margin = "10px"; // Margem entre imagens

                    imgWrapper.appendChild(imgElement);
                    previewContainer.appendChild(imgWrapper);
                };
            })(file, index);

            reader.readAsDataURL(file);  // Lê o arquivo para criar a URL da imagem
        });
    }

    // Inicializa o SortableJS para tornar o contêiner de imagens ordenável
    new Sortable(previewContainer, {
        animation: 150,
        ghostClass: 'sortable-ghost',
        onEnd: function (evt) {
            atualizarOrdenacaoImagens();  // Atualiza a ordem sempre que as imagens forem movidas
        }
    });

    document.getElementById('mensagem-imagem-principal').style.display = '';
}

function atualizarOrdenacaoImagens() {
    var previewContainer = document.getElementById('imagePreviewContainer');
    var wrappers = previewContainer.getElementsByClassName('image-wrapper');
    var order = [];

    // Coletar a nova ordem das imagens
    Array.from(wrappers).forEach(wrapper => {
        var index = wrapper.getAttribute('data-index');
        order.push(index);
    });

    // Armazenar a ordem no campo oculto
    document.getElementById('ordenacao-imagens').value = order.join(',');
}

function abrirModalConfirmacaoAlteracaoStatus(element) {
    let status = element.checked;
    element.checked = !element.checked;
    const elModal = document.getElementById('modal-confirmacao-alteracao-status');
    elModal.dataset.idproduto = element.dataset.idproduto;
    elModal.dataset.statusproduto = status;
    const modal = new bootstrap.Modal(elModal)

    modal.show()
}

function editarStatusProdutoBackOffice(element) {
    const elModal = document.getElementById('modal-confirmacao-alteracao-status');

    let produto = {
        id: elModal.dataset.idproduto,
        ativo: elModal.dataset.statusproduto
    };

    $.post("/produto/mudarStatus", produto, function(data) {
        if (data) {
            // let nomeInputStatusProduto = 'produto-status-' + data.idproduto
            // const inputStatusProduto = document.getElementById(nomeInputStatusProduto);
            // inputStatusProduto.checked = !inputStatusProduto.checked;
            window.location.href = "/produto/listarProdutosBackoffice";
        } else {
            abrirToastErro("Erro ao editar status do produto.");
        }
    }).fail(function(data) {
        if (data.responseText) {
            abrirToastErro(data.responseText);
        }
    });
}

