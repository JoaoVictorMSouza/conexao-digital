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
    $('#formulario-editar-produto').submit(async function(e) {
        e.preventDefault();
        let isInformacoesValidas = await validarInformacoesEditarProduto();
        if (isInformacoesValidas) {
            await editarProdutoBackOffice();
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    previewImages();
    atualizarOrdenacaoImagens();
  });

function previewImages() {
    var previewContainer = document.getElementById('imagePreviewContainer');
    previewContainer.innerHTML = ""; // Limpa as pré-visualizações anteriores

    let imagensProdutoBackoffice = document.getElementsByClassName('imagens-produto');

    for (let index = 0; index < imagensProdutoBackoffice.length; index++) {
        const imagem = imagensProdutoBackoffice[index];
        var imgWrapper = document.createElement("div");
        imgWrapper.classList.add("image-wrapper");
        imgWrapper.setAttribute("data-index", index); // Associa um índice à imagem

        var imgElement = document.createElement("img");
        imgElement.classList.add('produto-imagem');
        imgElement.src = imagem.currentSrc;
        imgElement.style.width = "150px";  // Define o tamanho da imagem
        imgElement.style.margin = "10px"; // Margem entre imagens

        imgWrapper.appendChild(imgElement);
        previewContainer.appendChild(imgWrapper);
    }

    var files = document.getElementById('imagens').files;

    filesArray = Array.from(files);  // Armazena arquivos em um array para manipulação

    if (filesArray) {
        filesArray.forEach((file, index) => {
            var reader = new FileReader();

            reader.onload = (function(f, i) {
                return function(e) {
                    var imgWrapper = document.createElement("div");
                    imgWrapper.classList.add("image-wrapper");
                    imgWrapper.setAttribute("data-index", (i + imagensProdutoBackoffice.length)); // Associa um índice à imagem

                    var imgElement = document.createElement("img");
                    imgElement.classList.add('produto-imagem');
                    imgElement.src = e.target.result;
                    imgElement.style.width = "150px";  // Define o tamanho da imagem
                    imgElement.style.margin = "10px"; // Margem entre imagens

                    imgWrapper.appendChild(imgElement);
                    previewContainer.appendChild(imgWrapper);

                    atualizarOrdenacaoImagens();
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

function validarInformacoesEditarProduto(){
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

async function editarProdutoBackOffice() {
    let formData = new FormData();

    formData.append('id', $("#id-produto").val());
    formData.append('nome', $("#nome").val());
    formData.append('avaliacao', $("#avaliacao").val());
    formData.append('descricaoDetalhada', $("#descricao-detalhada").val());
    formData.append('preco', $("#preco").val());
    formData.append('quantidadeEstoque', $("#qtd-estoque").val());

    const imageWrapper = document.getElementsByClassName('image-wrapper');
    let arrayFile = []

    for (let index = 0; index < imageWrapper.length; index++) {
        const wrapperEl = imageWrapper[index];

        const imagem = wrapperEl.querySelector('img');

        let response = await fetch(imagem.currentSrc);
        let blob = await response.blob();
        let file = new File([blob], `image_${index}.jpg`, { type: blob.type });
        arrayFile.push({ file: file, i: wrapperEl.dataset.index });
    }

    arrayFile.sort((a, b) => a.i - b.i);

    for (const fileObject of arrayFile) {
        formData.append('imagens', fileObject.file);
    }

    let ordenacaoImagens = $("#ordenacao-imagens").val();
    formData.append('ordenacaoImagens', ordenacaoImagens);

    $.ajax({
        url: '/produto/editar',
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
                abrirToastErro("Erro ao editar produto.");
            }
        },
        error: function(response) {
            if (response.responseText) {
                abrirToastErro(response.responseText);
            }
        }
    });
}