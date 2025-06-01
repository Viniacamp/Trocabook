package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.Set;

@Controller
public class LivrosController {

    @Autowired
    private UsuarioLivroRepository usuarioLivroRepository;

    private final Set<String> filtrosValidos = Set.of("TROCA", "VENDA", "AMBOS");

    @GetMapping("/livros")
    public String livros(Model model, HttpSession sessao, @RequestParam(value = "livroTipo", defaultValue = "todos") String filtroLivros) {
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        if (usuario != null){
            model.addAttribute("usuario", usuario);
        }
        Iterable<UsuarioLivro> usuarioLivros;
        if (filtrosValidos.contains(filtroLivros)){
            usuarioLivros = usuarioLivroRepository.findByTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf(filtroLivros));
        } else {
            usuarioLivros = usuarioLivroRepository.findAll();
        }
        LinkedList<String[]> listaUsuariosLivros = new LinkedList<>();
        for (UsuarioLivro usuarioLivro : usuarioLivros){
            String[] usuariosLivrosInfo = new String[5];
            usuariosLivrosInfo[0] = usuarioLivro.getLivro().getCapa();
            usuariosLivrosInfo[1] = usuarioLivro.getLivro().getNmLivro();
            usuariosLivrosInfo[2] = usuarioLivro.getUsuario().getFoto();
            usuariosLivrosInfo[3] = usuarioLivro.getUsuario().getNmUsuario();
            usuariosLivrosInfo[4] = Integer.toString(usuarioLivro.getCdUsuarioLivro());
            listaUsuariosLivros.add(usuariosLivrosInfo);

        }
        model.addAttribute("filtroLivros", filtroLivros);
        model.addAttribute("listaUsuariosLivros", listaUsuariosLivros);
        return "livros";
    }
}
