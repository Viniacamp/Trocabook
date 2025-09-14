package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.config.UserDetailsImpl; // 1. Importar UserDetailsImpl
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 2. Importar a anotação
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {
    @Autowired
    private UsuarioLivroRepository usuarioLivroRepository;

    @GetMapping("/chat/{cd}")
    // 3. REMOVEMOS HttpSession e ADICIONAMOS @AuthenticationPrincipal
    public String chat(@PathVariable int cd, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 4. A verificação manual de login foi REMOVIDA. Spring Security já protegeu a rota.

        // 5. Pegamos o usuário logado para usar na página (ex: mostrar sua foto de perfil)
        Usuario usuarioLogado = userDetails.getUsuario();
        model.addAttribute("usuarioLogado", usuarioLogado);

        // O resto da sua lógica continua igual
        UsuarioLivro usuarioLivro = usuarioLivroRepository.findByCdUsuarioLivro(cd);
        model.addAttribute("usuarioNegociante", usuarioLivro.getUsuario());
        model.addAttribute("livro", usuarioLivro.getLivro());

        return "chat";
    }
}