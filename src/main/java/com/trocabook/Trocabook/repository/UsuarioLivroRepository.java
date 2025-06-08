package com.trocabook.Trocabook.repository;

import java.util.List;

import com.trocabook.Trocabook.model.Livro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;

@Repository
public interface UsuarioLivroRepository extends CrudRepository<UsuarioLivro, Integer> {
	List<UsuarioLivro> findByUsuario(Usuario usuario);
	List<UsuarioLivro> findByUsuarioAndTipoNegociacao(Usuario usuario, UsuarioLivro.TipoNegociacao tipoNegociacao);
	Iterable<UsuarioLivro> findByTipoNegociacao(UsuarioLivro.TipoNegociacao tipoNegociacao);
	UsuarioLivro findByCdUsuarioLivro(int cd);
	List<UsuarioLivro> findByLivroIn(List<Livro> livros);
}
