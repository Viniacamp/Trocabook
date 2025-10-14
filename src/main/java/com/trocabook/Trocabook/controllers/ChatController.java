package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.config.UserDetailsImpl; // 1. Importar UserDetailsImpl
import com.trocabook.Trocabook.controllers.response.ChatResponse;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.model.dto.MensagemDTO;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import com.trocabook.Trocabook.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 2. Importar a anotação
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private UsuarioLivroRepository usuarioLivroRepository;

    private IChatService chatService;
    @Autowired
    public ChatController(UsuarioLivroRepository usuarioLivroRepository, IChatService chatService) {
        this.usuarioLivroRepository = usuarioLivroRepository;
        this.chatService = chatService;
    }

    @GetMapping("/{cd}")
    // 3. REMOVEMOS HttpSession e ADICIONAMOS @AuthenticationPrincipal
    public String chat(@PathVariable int cd, Model model, @RequestParam(name = "remetente", required = false) Integer cdUsuarioRemetente, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 4. A verificação manual de login foi REMOVIDA. Spring Security já protegeu a rota.

        // 5. Pegamos o usuário logado para usar na página (ex: mostrar sua foto de perfil)
        Usuario usuarioLogado = userDetails.getUsuario();
        model.addAttribute("usuarioLogado", usuarioLogado);
        UsuarioLivro usuarioLivro = usuarioLivroRepository.findByCdUsuarioLivro(cd);
        int cdUsuarioDestinatario;
        if (usuarioLivro.getUsuario().getCdUsuario() == usuarioLogado.getCdUsuario()) {
            cdUsuarioDestinatario = cdUsuarioRemetente;
        } else {
            cdUsuarioDestinatario = usuarioLivro.getUsuario().getCdUsuario();
        }

        // O resto da sua lógica continua igual

        ChatResponse<List<MensagemDTO>> mensagens = chatService.listarMensagensEntreUsuarios(cdUsuarioDestinatario, usuarioLogado.getCdUsuario());
        model.addAttribute("usuarioNegociante", usuarioLivro.getUsuario());
        model.addAttribute("mensagens", mensagens.getData());
        model.addAttribute("livro", usuarioLivro.getLivro());

        return "chat";
    }

    @GetMapping("/list-mensagens")
    public String listMensagens(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Usuario usuarioLogado = userDetails.getUsuario();
        ChatResponse<List<MensagemDTO>> mensagens = chatService.listarMensagensPorUsuarioDataEnvioDecrescente(usuarioLogado.getCdUsuario());
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("mensagens", mensagens.getData());
        return "list-mensagens";

    }

    
}