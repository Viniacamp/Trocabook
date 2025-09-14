package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.config.UserDetailsImpl; // 1. Importar UserDetailsImpl
import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.LivroRepository;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 2. Importar a anotação
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AnunciarLivroController {
    @Autowired
    private LivroRepository lr;

    @Autowired
    private UsuarioLivroRepository ulr;

    @GetMapping("/AnunciarLivro")
    // 3. REMOVEMOS a HttpSession. Não é mais necessária.
    public String anunciarLivro(Model model) {
        // 4. REMOVEMOS a verificação manual 'if (usuario == null)'.
        // O Spring Security já protegeu esta rota antes de chegar aqui.
        model.addAttribute("livro", new Livro());
        return "anunciar";
    }

    @PostMapping("/AnunciarLivro")
    public String anunciar(@RequestParam("capaLivro") MultipartFile capa,
                           @Valid Livro livro,
                           BindingResult result,
                           @RequestParam("tipoNegociacao") String tipoNegociacao,
                           // 5. REMOVEMOS a HttpSession e ADICIONAMOS @AuthenticationPrincipal
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           Model model) throws IOException {

        // 6. Pegamos o usuário logado diretamente do userDetails injetado pelo Spring Security.
        Usuario usuarioLogado = userDetails.getUsuario();

        // 7. A verificação 'if (usuario == null)' foi REMOVIDA.

        if (capa.isEmpty()) {
            model.addAttribute("capaErro", "Coloque a capa do livro");
            result.reject("capa");
        }

        if (tipoNegociacao == null || tipoNegociacao.isBlank()) {
            model.addAttribute("tipoErro", "Selecione o tipo de anúncio");
            result.reject("tipoNegociacao");
        }


        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            model.addAttribute("livro", livro);
            return "anunciar";
        }

        livro.setCapa(capa);
        lr.save(livro);

        UsuarioLivro usuarioLivro = new UsuarioLivro();
        // 8. Usamos o 'usuarioLogado' que pegamos do Spring Security.
        usuarioLivro.setUsuario(usuarioLogado);
        usuarioLivro.setLivro(livro);
        usuarioLivro.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf(tipoNegociacao));

        ulr.save(usuarioLivro);

        return "anuncioSucesso";
    }
}