package com.trocabook.Trocabook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/login")
	public String login(HttpSession sessao) {
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario != null) {
			return "redirect:/";
		}

		return "login";
	}

    @PostMapping("/login")
    public String logar(@RequestParam("email") String email,
                        @RequestParam("senha") String senha,
                        @RequestParam("g-recaptcha-response") String recaptchaToken,
                        HttpSession sessao,
                        Model model) {

        boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
        if (!isRecaptchaValid) {
            model.addAttribute("recaptchaError", "Falha na verificação reCAPTCHA. Tente novamente.");
            return "login"; // Retorna para a página de login com a mensagem de erro
        }

		model.addAttribute("senha", senha);
		model.addAttribute("email", email);
		if (email.isBlank() || senha.isBlank()) {
			model.addAttribute("mensagem", "Preencha Todos os Campos");
			return "login";
		}
		Usuario usuario = ur.findByEmailAndSenha(email, senha);
		if (usuario != null) {
			sessao.setAttribute("usuarioLogado", usuario);
		} else {
			model.addAttribute("mensagem", "Usuario não encontrado. Verifique o seu e-mail/senha e tente novamente");
			return "login";
		}
		return "redirect:/";
		
	}
	
	

}
