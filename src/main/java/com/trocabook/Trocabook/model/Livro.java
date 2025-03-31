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
	private int cd_livro;
	
	@NotBlank
	@Column(nullable = false)
	private String nm_livro;
	
	@NotNull
	@Column(nullable = false)
	private int ano_publicacao;
	
	@NotBlank
	@Column(nullable = false)
	private String capa;
	
	@OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
	private List<UsuarioLivro> usuarioLivros;
	
	@OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
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
	
	public int getCd_livro() {
		return cd_livro;
	}
	public void setCd_livro(int cd_livro) {
		this.cd_livro = cd_livro;
	}
	public String getNm_livro() {
		return nm_livro;
	}
	public void setNm_livro(String nm_livro) {
		this.nm_livro = nm_livro;
	}
	public int getAno_publicacao() {
		return ano_publicacao;
	}
	public void setAno_publicacao(int ano_publicacao) {
		this.ano_publicacao = ano_publicacao;
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
