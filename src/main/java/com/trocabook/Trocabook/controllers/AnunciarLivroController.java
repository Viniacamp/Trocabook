package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.config.UserDetailsImpl;
import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.service.LivroService; // Importar o LivroService
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // A única dependência que o controller precisa é do serviço
    @Autowired
    private LivroService livroService;

    @GetMapping("/AnunciarLivro")
    public String anunciarLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "anunciar";
    }

    @PostMapping("/AnunciarLivro")
    public String anunciar(@RequestParam("capaLivro") MultipartFile capa,
                           @Valid Livro livro,
                           BindingResult result,
                           @RequestParam("tipoNegociacao") String tipoNegociacao,
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           Model model) throws IOException {

        Usuario usuarioLogado = userDetails.getUsuario();

        if (capa.isEmpty()) {
            model.addAttribute("capaErro", "Coloque a capa do livro");
            result.reject("capa");
        }
        if (tipoNegociacao == null || tipoNegociacao.isBlank()) {
            model.addAttribute("tipoErro", "Selecione o tipo de anúncio");
            result.reject("tipoNegociacao");
        }
        if (result.hasErrors()) {
            model.addAttribute("livro", livro);
            return "anunciar";
        }

        // AGORA A LÓGICA É UMA ÚNICA CHAMADA PARA O SERVIÇO
        livroService.anunciarNovoLivro(livro, capa, usuarioLogado, tipoNegociacao);

        return "anuncioSucesso";
    }
}