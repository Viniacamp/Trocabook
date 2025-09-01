package com.trocabook.Trocabook.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // 1. IMPORTAR @Value
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.repository.UsuarioRepository;
import com.trocabook.Trocabook.service.RecaptchaService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CadastroController {

	@Autowired
	private UsuarioRepository ur;

	@Autowired
	private RecaptchaService recaptchaService;


	@Value("${google.recaptcha.key.site}")
	private String recaptchaSiteKey;

	@GetMapping("/cadastro")
	public String cadastro(Model model, HttpSession sessao) {
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario != null) {
			return "redirect:/";
		}
		model.addAttribute("usuario", new Usuario());

		model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);

		return "cadastro";
	}

	@PostMapping("/cadastro")
	public String cadastrar(@RequestParam("fotoA") MultipartFile foto,
							@RequestParam("g-recaptcha-response") String recaptchaToken,
							@Valid Usuario usuario,
							BindingResult result,
							Model model,
							RedirectAttributes attributes) throws IOException {

		boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
		if (!isRecaptchaValid) {
			attributes.addFlashAttribute("recaptchaError", "Falha na verificação reCAPTCHA. Tente novamente.");
			return "redirect:/cadastro";
		}

		if (foto.isEmpty()) {
			model.addAttribute("fotoErro", "Selecione uma foto válida");
			result.reject("fotoA");
		}

		if (ur.findByEmail(usuario.getEmail()) != null) {
			result.rejectValue("email", "email.exists", "O Email inserido já está cadastrado no sistema");
		}

		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
			return "cadastro";
		}
		usuario.setFoto(foto);
		usuario.setStatus('A');

		ur.save(usuario);
		return "redirect:/login";
	}
}
