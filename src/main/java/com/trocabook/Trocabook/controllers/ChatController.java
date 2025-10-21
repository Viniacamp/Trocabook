package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.config.UserDetailsImpl;
import com.trocabook.Trocabook.controllers.response.ChatResponse;
import com.trocabook.Trocabook.model.Usuario;

import com.trocabook.Trocabook.model.UsuarioLivro;

import com.trocabook.Trocabook.model.dto.MensagemDTO;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import com.trocabook.Trocabook.repository.UsuarioRepository;
import com.trocabook.Trocabook.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
@RequestMapping("/chat")
public class ChatController {

    private final UsuarioLivroRepository usuarioLivroRepository;
    private final UsuarioRepository usuarioRepository;
    private final IChatService chatService;

    @Autowired
    public ChatController(UsuarioLivroRepository usuarioLivroRepository, IChatService chatService, UsuarioRepository usuarioRepository) {
        this.usuarioLivroRepository = usuarioLivroRepository;
        this.chatService = chatService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/conversar")
    public String conversar(
            @RequestParam("usuarioLivro") int cdUsuarioLivro,
            @RequestParam("remetente") int cdUsuarioRemetente,
            @RequestParam(name = "destinatario", required = false) Integer cdUsuarioDestinatario,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ) {
        Usuario usuarioLogado = userDetails.getUsuario();

        if (cdUsuarioRemetente != usuarioLogado.getCdUsuario()) {
            throw new SecurityException("Tentativa de acesso indevido a conversa de outro usuário");
        }

        UsuarioLivro usuarioLivro = usuarioLivroRepository.findByCdUsuarioLivro(cdUsuarioLivro);


        if (cdUsuarioDestinatario == null) {
            cdUsuarioDestinatario = usuarioLivro.getUsuario().getCdUsuario();
        }


        ChatResponse<List<MensagemDTO>> mensagens = chatService.listarMensagensEntreUsuarios(
                usuarioLivro.getCdUsuarioLivro(),
                cdUsuarioDestinatario,
                cdUsuarioRemetente
        );

        if (mensagens == null || mensagens.getData() == null) {
            mensagens = new ChatResponse<>(List.of(), "success");
        }

        Usuario usuarioNegociante;

        if (cdUsuarioDestinatario == usuarioLivro.getUsuario().getCdUsuario()) {
            usuarioNegociante = usuarioLivro.getUsuario();
        } else {
            usuarioNegociante = usuarioRepository.findById(cdUsuarioDestinatario).get();
        }



        MensagemDTO mensagemDTO = new MensagemDTO();
        mensagemDTO.setCdUsuarioDestinatario(cdUsuarioDestinatario);
        mensagemDTO.setCdUsuarioLivro(usuarioLivro.getCdUsuarioLivro());
        mensagemDTO.setCdUsuarioRemetente(cdUsuarioRemetente);

        model.addAttribute("mensagemDTO", mensagemDTO);
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("usuarioNegociante", usuarioNegociante);
        model.addAttribute("livro", usuarioLivro.getLivro());
        model.addAttribute("mensagens", mensagens.getData());

        return "chat";
    }

    @GetMapping("/list-mensagens")
    public String listMensagens(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Usuario usuarioLogado = userDetails.getUsuario();
        ChatResponse<List<MensagemDTO>> mensagens = chatService.listarMensagensPorUsuarioDataEnvioDecrescente(usuarioLogado.getCdUsuario());

        if (mensagens == null || mensagens.getData().isEmpty()) {
            model.addAttribute("mensagemVazia", "Nenhuma conversa iniciada");
        } else {
            model.addAttribute("usuarioLogado", usuarioLogado);
            model.addAttribute("mensagens", mensagens.getData());
        }

        return "list-mensagens";
    }

    // 🔹 Envio de mensagem (via fetch)
    @PostMapping("/mensagens")
    @ResponseBody
    public ChatResponse<MensagemDTO> salvarMensagemAjax(@RequestBody MensagemDTO mensagemDTO) {
        return chatService.enviarMensagem(mensagemDTO);
    }

    // 🔹 Atualização automática (polling)
    @GetMapping("/mensagens/atualizar")
    @ResponseBody
    public ChatResponse<List<MensagemDTO>> atualizarMensagens(
            @RequestParam("usuarioLivro") int cdUsuarioLivro,
            @RequestParam("remetente") int cdUsuarioRemetente,
            @RequestParam("destinatario") int cdUsuarioDestinatario
    ) {
        ChatResponse<List<MensagemDTO>> mensagens = chatService.listarMensagensEntreUsuarios(
                cdUsuarioLivro,
                cdUsuarioDestinatario,
                cdUsuarioRemetente
        );
        if (mensagens == null || mensagens.getData() == null) {
            mensagens = new ChatResponse<>(List.of(), "success");
        }

        return mensagens;
    }
}
