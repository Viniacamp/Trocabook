package com.trocabook.Trocabook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // 1. IMPORTAR @Value
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.repository.UsuarioRepository;
import com.trocabook.Trocabook.service.RecaptchaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {


	@Autowired
	private RecaptchaService recaptchaService;

	@Value("${google.recaptcha.key.site}")
	private String recaptchaSiteKey;

	@GetMapping("/login")
	public String login(HttpSession sessao, Model model) { // Adicionado Model aqui
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario != null) {
			return "redirect:/";
		}

		model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);

		return "login";
	}


}
