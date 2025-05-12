package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {
    @Autowired
    private UsuarioLivroRepository usuarioLivroRepository;

    @GetMapping("/chat/{cd}")
    public String chat(@PathVariable int cd, Model model, HttpSession sessao) {
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
        if (usuarioLogado == null){
            return "redirect:/login";
        }
        UsuarioLivro usuarioLivro = usuarioLivroRepository.findByCdUsuarioLivro(cd);
        model.addAttribute("usuarioNegociante", usuarioLivro.getUsuario());
        model.addAttribute("livro", usuarioLivro.getLivro());
        return "chat";
    }
}
