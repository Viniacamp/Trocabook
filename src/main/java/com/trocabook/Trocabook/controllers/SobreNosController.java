package com.trocabook.Trocabook.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trocabook.Trocabook.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class SobreNosController {
	@GetMapping("/sobreNos")
	public String sobreNos(HttpSession sessao, Model model) {
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
		}
		return "sobrenos";
	}

}
