package com.trocabook.Trocabook.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.trocabook.Trocabook.model.Negociacao;
import com.trocabook.Trocabook.repository.NegociacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MeusLivrosController {
	
	@Autowired
	private UsuarioLivroRepository url;

	@Autowired
	private NegociacaoRepository nr;

	private final Set<String> tiposValidos = Set.of("VENDA", "TROCA", "AMBOS");
	
	@GetMapping("/MeusLivros")
	public String meusLivros(HttpSession sessao, Model model, @RequestParam(value = "filtroAnuncio", defaultValue = "todos") String filtroAnuncio, @RequestParam(value="filtroT/V", defaultValue = "todos") String filtroTroVen) {
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
	    if (usuario == null) {
	        return "redirect:/";
	    }
	    List<Livro> livrosAnuncio = new LinkedList<>();
		List<Livro> livrosNegociacao = new LinkedList<>();
	    List<UsuarioLivro> listaAnuncio;
		List<Negociacao> listaNegocio;

		if (tiposValidos.contains(filtroAnuncio)) {
			listaAnuncio = url.findByUsuarioAndTipoNegociacao(usuario, UsuarioLivro.TipoNegociacao.valueOf(filtroAnuncio));
		} else {
			listaAnuncio = url.findByUsuario(usuario);
		}

		if (tiposValidos.contains(filtroTroVen)) {
			listaNegocio = nr.findByUsuarioAnuncianteAndTipo(usuario, Negociacao.Tipo.valueOf(filtroTroVen));
		} else {
			listaNegocio = nr.findByUsuarioAnunciante(usuario);
		}

	    for (UsuarioLivro usuarioLivro : listaAnuncio) {
	    	livrosAnuncio.add(usuarioLivro.getLivro());
	    }

		for (Negociacao negociacao : listaNegocio){
			livrosNegociacao.add(negociacao.getLivro());
		}
	    
		model.addAttribute("livrosAnuncio", livrosAnuncio);
		model.addAttribute("livrosNegociacao", livrosNegociacao);
		model.addAttribute("filtroSelecionadoAnuncio", filtroAnuncio);
		model.addAttribute("filtroSelecionadoTroVen", filtroTroVen);
	    return "meusLivros";
	}
}
