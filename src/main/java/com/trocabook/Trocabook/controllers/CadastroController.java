package com.trocabook.Trocabook.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.repository.UsuarioRepository;

@Controller
public class CadastroController {
	@Autowired
	private UsuarioRepository ur;
	
	@GetMapping("/cadastro")
	public String cadastro() {
		return "cadastro";
	}
	
	@PostMapping("/cadastro")
	public String cadastrar(@RequestParam("fotoA") MultipartFile foto, Usuario usuario) throws IOException{
		if (!foto.isEmpty()) {
			usuario.setFoto(foto);
		}
		usuario.setStatus('A');
		ur.save(usuario);
		return "redirect:/login";
	}

}
