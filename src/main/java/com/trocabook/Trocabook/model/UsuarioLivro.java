package com.trocabook.Trocabook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;

@Entity
public class UsuarioLivro {
	@Valid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cd_usuariolivro;
	
	@ManyToOne
	@JoinColumn(name = "cd_usuario", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "cd_livro", nullable = false)
	private Livro livro;
	
	public int getCd_usuariolivro() {
		return cd_usuariolivro;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public void setCd_usuariolivro(int cd_usuariolivro) {
		this.cd_usuariolivro = cd_usuariolivro;
	}
}
