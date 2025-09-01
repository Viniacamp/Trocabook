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
	private UsuarioRepository ur;

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

	@PostMapping("/login")
	public String logar(@RequestParam("email") String email,
						@RequestParam("senha") String senha,
						@RequestParam("g-recaptcha-response") String recaptchaToken,
						HttpSession sessao,
						Model model) {

		model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);

		boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
		if (!isRecaptchaValid) {
			model.addAttribute("recaptchaError", "Falha na verificação reCAPTCHA. Tente novamente.");
			return "login";
		}

		model.addAttribute("email", email);
		if (email.isBlank() || senha.isBlank()) {
			model.addAttribute("mensagem", "Preencha Todos os Campos");
			return "login";
		}

		Usuario usuario = ur.findByEmailAndSenha(email, senha);
		if (usuario != null) {
			sessao.setAttribute("usuarioLogado", usuario);
		} else {
			model.addAttribute("mensagem", "Usuário não encontrado. Verifique o seu e-mail/senha e tente novamente");
			return "login";
		}
		return "redirect:/";
	}
}
