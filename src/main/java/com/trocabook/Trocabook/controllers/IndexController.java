package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.repository.LivroRepository;
import com.trocabook.Trocabook.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.trocabook.Trocabook.model.Usuario;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private LivroRepository livroRepository;

	@GetMapping("/")
	public String index(Model model, HttpSession sessao) {
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
		}
		List<Usuario> destaques = usuarioRepository.findTop6ByOrderByAvaliacaoDesc();
		model.addAttribute("destaques", destaques);
		return "index";
	}
	
	@PostMapping("/deslogar")
	public String deslogar(HttpSession sessao) {
		sessao.invalidate();
		return "redirect:/";
	}

	/* @GetMapping("/pesquisar")
	@ResponseBody
	public List<Livro> pesquisar(@RequestParam(name="titulo", required = false) String nm_livro){
		return livroRepository.findByNmLivroContainingIgnoreCase(nm_livro);
	}*/
	
	

}
