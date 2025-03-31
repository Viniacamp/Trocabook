package com.trocabook.Trocabook.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Negociacao {
	@Valid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cd_negociacao;
	
	@ManyToOne
	@JoinColumn(name = "cd_usuarioAnunciante", nullable = false)
	private Usuario usuarioAnunciante;
	
	@ManyToOne
	@JoinColumn(name = "cd_usuarioInteressado", nullable = false)
	private Usuario usuarioInteressado;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime dt_negociacao;
	
	@ManyToOne
	@JoinColumn(name = "cd_livro", nullable = false)
	private Livro livro;
	
	@NotBlank
	@Column(nullable = false)
	private char tipo_negociacao;
	
	public int getCd_negociacao() {
		return cd_negociacao;
	}

	public void setCd_negociacao(int cd_negociacao) {
		this.cd_negociacao = cd_negociacao;
	}

	public Usuario getUsuarioAnunciante() {
		return usuarioAnunciante;
	}

	public void setUsuarioAnunciante(Usuario usuarioAnunciante) {
		this.usuarioAnunciante = usuarioAnunciante;
	}

	public Usuario getUsuarioInteressado() {
		return usuarioInteressado;
	}

	public void setUsuarioInteressado(Usuario usuarioInteressado) {
		this.usuarioInteressado = usuarioInteressado;
	}

	public LocalDateTime getDt_negociacao() {
		return dt_negociacao;
	}

	public void setDt_negociacao(LocalDateTime dt_negociacao) {
		this.dt_negociacao = dt_negociacao;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public char getTipo_negociacao() {
		return tipo_negociacao;
	}

	public void setTipo_negociacao(char tipo_negociacao) {
		this.tipo_negociacao = tipo_negociacao;
	}

	
	
	
}
