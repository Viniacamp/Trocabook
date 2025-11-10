package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.config.UserDetailsImpl;
import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.dto.LivroDTO;
import com.trocabook.Trocabook.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class AnunciarLivroController {

    // A única dependência que o controller precisa é do serviço
    @Autowired
    private LivroService livroService;

    @GetMapping("/AnunciarLivro")
    public String anunciarLivroApi(Model model) {
        model.addAttribute("livroDTO", new LivroDTO());
        return "anunciar";
    }

    @PostMapping("/AnunciarLivro")
    public String anunciar(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute("livroDTO") LivroDTO livroDTO, Model model) throws IOException {

        Usuario usuarioLogado = userDetails.getUsuario();

        boolean temErro = false;

        // --- Validações ---
        if (livroDTO.getTitulo() == null || livroDTO.getTitulo().isBlank()) {
            model.addAttribute("erroTitulo", "O título do livro é obrigatório.");
            temErro = true;
        }

        if (livroDTO.getTipoNegociacao() == null || livroDTO.getTipoNegociacao().isBlank()) {
            model.addAttribute("erroTipo", "Selecione o tipo de negociação.");
            temErro = true;
        }

        if (livroDTO.getAutores() == null || livroDTO.getAutores().isEmpty()) {
            model.addAttribute("erroAutor", "O livro precisa ter pelo menos um autor.");
            temErro = true;
        }

        if (livroDTO.getCategorias() == null || livroDTO.getCategorias().isEmpty()) {
            model.addAttribute("erroCategoria", "Selecione pelo menos uma categoria.");
            temErro = true;
        }
        System.out.println(livroDTO.getThumbnailUrl());
        // Define uma capa padrão caso o thumbnail esteja ausente
        if (livroDTO.getThumbnailUrl() == null || livroDTO.getThumbnailUrl().isBlank()) {
            livroDTO.setThumbnailUrl("/img/default-capa.png");
        }

        // Se houver erros, volta pra página e mantém os dados digitados
        if (temErro) {
            model.addAttribute("livroDTO", livroDTO);
            return "anunciar";
        }

        // AGORA A LÓGICA É UMA ÚNICA CHAMADA PARA O SERVIÇO
        livroService.anunciarNovoLivro(livroDTO, usuarioLogado);

        return "anuncioSucesso";
    }

    @PostMapping("/buscar")
    @ResponseBody
    public List<LivroDTO> buscar(@RequestBody LivroDTO livro) {
        System.out.println("Buscando livro");
        System.out.println(livro);
        return livroService.listarLivrosApi(livro.getTitulo());
    }




}