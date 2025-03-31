package com.trocabook.Trocabook.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		return "anunciar";
	}
	
	@PostMapping("/AnunciarLivro")
	public String anunciar(@RequestParam("capaLivro") MultipartFile capa, Livro livro, HttpSession sessao) throws IOException{
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario == null) {
			return "redirect:/";
		}
		if (capa != null) {
			livro.setCapa(capa);
		}
		lr.save(livro);
		UsuarioLivro usuarioLivro = new UsuarioLivro();
		usuarioLivro.setUsuario(usuario);
		usuarioLivro.setLivro(livro);
		ulr.save(usuarioLivro);
		return "redirect:/MeusLivros";
	}
}
