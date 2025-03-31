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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Usuario {
	@Valid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cd_usuario;
	
	@NotBlank
	@Column(nullable = false)
	private String nm_usuario;
	
	@NotBlank
	@Column(nullable = false, unique = true, updatable = false)
	private String CPF;
	
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank
	@Column(nullable = false)
	private String senha;
	
	@NotBlank
	private String foto;
	
	@NotNull
	@Column(nullable = false)
	private char status;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<UsuarioLivro> usuarioLivros;
	
	@OneToMany(mappedBy = "usuarioAnunciante", cascade = CascadeType.ALL)
	private List<Negociacao> negociacoesAnunciante;
	
	@OneToMany(mappedBy = "usuarioInteressado", cascade = CascadeType.ALL)
	private List<Negociacao> negociacoesInteressado;
	
	public List<Negociacao> getNegociacoesAnunciante() {
		return negociacoesAnunciante;
	}

	public void setNegociacoesAnunciante(List<Negociacao> negociacoesAnunciante) {
		this.negociacoesAnunciante = negociacoesAnunciante;
	}

	public List<Negociacao> getNegociacoesInteressado() {
		return negociacoesInteressado;
	}

	public void setNegociacoesInteressado(List<Negociacao> negociacoesInteressado) {
		this.negociacoesInteressado = negociacoesInteressado;
	}

	public List<UsuarioLivro> getUsuarioLivros() {
		return usuarioLivros;
	}
	
	public void setUsuarioLivros(List<UsuarioLivro> usuarioLivros) {
		this.usuarioLivros = usuarioLivros;
	}
	public String getNm_usuario() {
		return nm_usuario;
	}
	public void setNm_usuario(String nm_usuario) {
		this.nm_usuario = nm_usuario;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            FileStorageServiceUsuario fs = new FileStorageServiceUsuario();
            this.foto = fs.armazenarArquivoUsuario(foto);
        }
    }
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public int getCd_usuario() {
		return cd_usuario;
	}
	public void setCd_usuario(int cd_usuario) {
		this.cd_usuario = cd_usuario;
	}
	
}
