package com.trocabook.Trocabook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioRepository ur;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String logar(@RequestParam("email") String email, @RequestParam("senha") String senha, HttpSession sessao) {
		Usuario usuario = ur.findByEmailAndSenha(email, senha);
		if (usuario != null) {
			sessao.setAttribute("usuarioLogado", usuario);
		}
		return "redirect:/";
		
	}
	
	

}
