package com.trocabook.Trocabook.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.LivroRepository;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AnunciarLivroController {
	@Autowired
	private LivroRepository lr;
	
	@Autowired
	private UsuarioLivroRepository ulr;



	
	@GetMapping("/AnunciarLivro")
	public String anunciarLivro(Model model, HttpSession sessao) {
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario == null) {
			return "redirect:/";
		}
		model.addAttribute("livro", new Livro());
		return "anunciar";
	}
	
	@PostMapping("/AnunciarLivro")
	public String anunciar(@RequestParam("capaLivro") MultipartFile capa, @Valid Livro livro, BindingResult result,@RequestParam("tipoNegociacao") String tipoNegociacao, HttpSession sessao, Model model) throws IOException{
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario == null) {
			return "redirect:/";
		}
		if (capa.isEmpty()) {
			model.addAttribute("capaErro", "Coloque a capa do livro");
			result.reject("capa");
		}

		if (tipoNegociacao == null || tipoNegociacao.isBlank()) {
			model.addAttribute("tipoErro", "Selecione o tipo de anúncio");
			result.reject("tipoNegociacao");
		}


		if (result.hasErrors()){
			System.out.println(result.getAllErrors());
			model.addAttribute("livro", livro);
			return "anunciar";
		}
		livro.setCapa(capa);
		lr.save(livro);
		UsuarioLivro usuarioLivro = new UsuarioLivro();
		usuarioLivro.setUsuario(usuario);
		usuarioLivro.setLivro(livro);
		usuarioLivro.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf(tipoNegociacao));
		ulr.save(usuarioLivro);
		return "redirect:/MeusLivros";
	}
}
