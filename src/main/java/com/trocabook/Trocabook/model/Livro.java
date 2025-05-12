package com.trocabook.Trocabook.model;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.trocabook.Trocabook.service.FileStorageServiceUsuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Livro {
	@Valid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cdLivro;
	
	@NotBlank(message = "Preencha o Título")
	@Column(nullable = false)
	private String nmLivro;
	
	@NotNull(message = "Preencha o Ano de Publicação")
	@Column(nullable = false)
	private Integer anoPublicacao;

	@Column(nullable = false)
	private String capa;
	
	@OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
	// @JsonIgnore
	private List<UsuarioLivro> usuarioLivros;
	
	@OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
	// @JsonIgnore
	private List<Negociacao> negociacoes;
	
	public List<UsuarioLivro> getUsuarioLivros() {
		return usuarioLivros;
	}
	
	public void setUsuarioLivros(List<UsuarioLivro> usuarioLivros) {
		this.usuarioLivros = usuarioLivros;
	}
	
	public List<Negociacao> getNegociacoes() {
		return negociacoes;
	}
	
	public void setNegociacoes(List<Negociacao> negociacoes) {
		this.negociacoes = negociacoes;
	}
	
	public int getCdLivro() {
		return cdLivro;
	}
	public void setCdLivro(int cdLivro) {
		this.cdLivro = cdLivro;
	}
	public String getNmLivro() {
		return nmLivro;
	}
	public void setNmLivro(String nmLivro) {
		this.nmLivro = nmLivro;
	}
	public Integer getAnoPublicacao() {
		return anoPublicacao;
	}
	public void setAnoPublicacao(Integer anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}
	public String getCapa() {
		return capa;
	}
	public void setCapa(String capa) {
		this.capa = capa;
	}
	public void setCapa(MultipartFile capa) throws IOException {
        if (capa != null && !capa.isEmpty()) {
            FileStorageServiceUsuario fs = new FileStorageServiceUsuario();
            this.capa = fs.armazenarArquivoLivro(capa);
        }
    }
	
}
