package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Negociacao;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.NegociacaoRepository;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import com.trocabook.Trocabook.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;

@Controller
public class DestaqueController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioLivroRepository usuarioLivroRepository;

    @Autowired
    private NegociacaoRepository negociacaoRepository;


    @GetMapping("/destaque/{id}")
    public String destaque(HttpSession sessao, Model model, @PathVariable int id) {
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
        if (usuarioLogado != null) {
            model.addAttribute("usuarioLogin", usuarioLogado);
        }
        Usuario usuarioDestaque = usuarioRepository.findById(id).orElse(null);
        if (usuarioDestaque == null) {
            return "redirect:/";
        }
        Long numeroTrocas = negociacaoRepository.contarNegociacoesPorUsuarioETipo(usuarioDestaque, Negociacao.Tipo.TROCA);
        Long numeroVendas = negociacaoRepository.contarNegociacoesPorUsuarioETipo(usuarioDestaque, Negociacao.Tipo.VENDA);
        List<UsuarioLivro> usuarioLivros = usuarioLivroRepository.findByUsuario(usuarioDestaque);
        List<Livro> livrosDestaque = new LinkedList<>();

        for (UsuarioLivro usuarioLivro : usuarioLivros) {
            livrosDestaque.add(usuarioLivro.getLivro());
        }

        model.addAttribute("usuarioDestaque", usuarioDestaque);
        model.addAttribute("qtd_trocas", numeroTrocas);
        model.addAttribute("qtd_vendas", numeroVendas);
        model.addAttribute("livros", livrosDestaque);

        return "destaque";
    }
}
