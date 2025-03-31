package com.trocabook.Trocabook.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MeusLivrosController {
	
	@Autowired
	private UsuarioLivroRepository url;
	
	@GetMapping("/MeusLivros")
	public ModelAndView meusLivros(HttpSession sessao) {
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
	    if (usuario == null) {
	        return new ModelAndView("redirect:/");
	    }
	    List<Livro> livros = new LinkedList<>();
	    List<UsuarioLivro> lista = url.findByUsuario(usuario);
	    for (UsuarioLivro usuarioLivro : lista) {
	    	livros.add(usuarioLivro.getLivro());
	    }
	    
	    ModelAndView mv = new ModelAndView("meusLivros");
	    mv.addObject("livros", livros);
	    return mv;
	}
}
