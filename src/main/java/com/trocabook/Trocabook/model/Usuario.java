package com.trocabook.Trocabook.model;


import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import com.trocabook.Trocabook.service.FileStorageServiceUsuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Usuario {
	@Valid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cdUsuario;

    @NotBlank(message = "Preencha o Nome")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇ ]+$", message = "O nome deve conter apenas letras e espaços.")
    @Column(nullable = false)
    private String nmUsuario;

    @CPF(message = "CPF inválido")
    @Column(nullable = false, unique = true, updatable = false)
    private String CPF;

	@NotBlank(message = "Preencha o E-mail")
	@Email(message = "Preencha com um E-mail válido")
	@Column(nullable = false, unique = true)
	private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial (@$!%*?&).")
    @Column(nullable = false)
    private String senha;

	private String foto;
	
	@NotNull
	@Column(nullable = false)
	private char status;



	@Max(5)
	@Column(nullable = false)
	private double avaliacao;
	
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
	public String getNmUsuario() {
		return nmUsuario;
	}
	public void setNmUsuario(String nmUsuario) {
		this.nmUsuario = nmUsuario;
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
	public int getCdUsuario() {
		return cdUsuario;
	}
	public void setCdUsuario(int cdUsuario) {
		this.cdUsuario = cdUsuario;
	}
	public double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(double avaliacao) {
		this.avaliacao = avaliacao;
	}
	
}
