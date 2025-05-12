package com.trocabook.Trocabook.controllers;

import com.trocabook.Trocabook.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagAnuncioController {
    @GetMapping("/anuncio")
	public String pagAnuncio(HttpSession sessao, Model model) {
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
		}
		return "paganuncio";
	}
}
