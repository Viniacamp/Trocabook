package com.trocabook.Trocabook.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CadastroController {

	@Autowired
	private UsuarioRepository ur;
	
	@GetMapping("/cadastro")
	public String cadastro(Model model, HttpSession sessao) {
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario != null) {
			return "redirect:/";
		}
		model.addAttribute("usuario", new Usuario());
		return "cadastro";
	}
	
	@PostMapping("/cadastro")
	public String cadastrar(@RequestParam("fotoA") MultipartFile foto, @Valid Usuario usuario, BindingResult result, Model model) throws IOException{

		if (foto.isEmpty()) {
			model.addAttribute("fotoErro", "Selecione uma foto válida");
			result.reject("fotoA");
		}

		if (ur.findByEmail(usuario.getEmail()) != null) {
			result.rejectValue("email", "email.exists", "O Email inserido já está cadastrado no sistema");
		}


		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.addAttribute("usuario", usuario);
			return "cadastro";
		}
		usuario.setFoto(foto);
		usuario.setStatus('A');

		ur.save(usuario);
		return "redirect:/login";
	}

}
