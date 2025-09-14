package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.config.UserDetailsImpl; // 1. Importar UserDetailsImpl
import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Negociacao;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.NegociacaoRepository;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 2. Importar a anotação
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class MeusLivrosController {

    @Autowired
    private UsuarioLivroRepository url;

    @Autowired
    private NegociacaoRepository nr;

    private final Set<String> tiposValidos = Set.of("VENDA", "TROCA", "AMBOS");

    @GetMapping("/MeusLivros")
    public String meusLivros(Model model,
                             @RequestParam(value = "filtroAnuncio", defaultValue = "todos") String filtroAnuncio,
                             @RequestParam(value="filtroT/V", defaultValue = "todos") String filtroTroVen,
                             // 3. REMOVEMOS HttpSession e ADICIONAMOS @AuthenticationPrincipal
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 4. A verificação manual de login foi REMOVIDA.

        // 5. Pegamos o usuário logado diretamente do userDetails.
        Usuario usuarioLogado = userDetails.getUsuario();

        List<Livro> livrosAnuncio = new LinkedList<>();
        List<Livro> livrosNegociacao = new LinkedList<>();
        List<UsuarioLivro> listaAnuncio;
        List<Negociacao> listaNegocio;

        // 6. Usamos o 'usuarioLogado' nas consultas ao repositório.
        if (tiposValidos.contains(filtroAnuncio)) {
            listaAnuncio = url.findByUsuarioAndTipoNegociacao(usuarioLogado, UsuarioLivro.TipoNegociacao.valueOf(filtroAnuncio));
        } else {
            listaAnuncio = url.findByUsuario(usuarioLogado);
        }

        if (tiposValidos.contains(filtroTroVen)) {
            listaNegocio = nr.findByUsuarioAnuncianteAndTipo(usuarioLogado, Negociacao.Tipo.valueOf(filtroTroVen));
        } else {
            listaNegocio = nr.findByUsuarioAnunciante(usuarioLogado);
        }

        for (UsuarioLivro usuarioLivro : listaAnuncio) {
            livrosAnuncio.add(usuarioLivro.getLivro());
        }

        for (Negociacao negociacao : listaNegocio){
            livrosNegociacao.add(negociacao.getLivro());
        }

        model.addAttribute("livrosAnuncio", livrosAnuncio);
        model.addAttribute("livrosNegociacao", livrosNegociacao);
        model.addAttribute("filtroSelecionadoAnuncio", filtroAnuncio);
        model.addAttribute("filtroSelecionadoTroVen", filtroTroVen);

        return "meusLivros";
    }
}