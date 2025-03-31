package com.trocabook.Trocabook.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.trocabook.Trocabook.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	@GetMapping("/")
	public String index(Model model, HttpSession sessao) {
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
		}
		return "index";
	}
	
	@PostMapping("/deslogar")
	public String deslogar(HttpSession sessao) {
		sessao.invalidate();
		return "redirect:/";
	}
	
	

}
