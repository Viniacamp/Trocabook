document.getElementById("pesquisa").addEventListener("input", function(){
    const titulo = this.value;
    let div = document.getElementById("resultadosPesquisa");
    div.innerHTML = "";
    if (titulo.length >= 1){
        fetch(`/pesquisar?titulo=${encodeURIComponent(titulo)}`)
            .then(res => {
                if (!res.ok){
                    throw new Error("Falha ao encontrar livro");
                }
                return res.json();
            })
            .then(Usuariolivros => {
                if (Usuariolivros.length === 0){
                    div.innerHTML += "<p>Nenhum Livro encontrado</p>"
                }
                else {
                    Usuariolivros.forEach(Usuariolivro => {
                        const link = document.createElement("a");
                        link.href = "/chat/" + Usuariolivro[3];
                        const capa = document.createElement("img");
                        capa.src = Usuariolivro[0];
                        capa.className = "livro-capa";

                        const texto = document.createElement("p");
                        texto.innerText = Usuariolivro[1];

                        const vendedor = document.createElement("img");
                        vendedor.src = Usuariolivro[2];
                        vendedor.className = "perfil-foto";


                        link.appendChild(capa);
                        link.appendChild(texto);
                        link.appendChild(vendedor);
                        div.appendChild(link);

                    })
                }
            })
            .catch(erro => {
                div.innerHTML += `<p>Ocorreu um erro ao buscar o livro=${erro.message}</p>`
            })
    }
})