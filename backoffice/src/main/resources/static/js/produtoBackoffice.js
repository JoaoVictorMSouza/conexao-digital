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