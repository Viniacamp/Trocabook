package com.trocabook.Trocabook.model;

import com.trocabook.Trocabook.service.FileStorageServiceUsuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

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

    // A MUDANÇA PRINCIPAL ESTÁ AQUI:
    // Removemos as anotações @NotBlank, @Size e @Pattern da senha.
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

    // --- Getters e Setters (permanecem os mesmos) ---

    public List<Negociacao> getNegociacoesAnunciante() { return negociacoesAnunciante; }
    public void setNegociacoesAnunciante(List<Negociacao> negociacoesAnunciante) { this.negociacoesAnunciante = negociacoesAnunciante; }
    public List<Negociacao> getNegociacoesInteressado() { return negociacoesInteressado; }
    public void setNegociacoesInteressado(List<Negociacao> negociacoesInteressado) { this.negociacoesInteressado = negociacoesInteressado; }
    public List<UsuarioLivro> getUsuarioLivros() { return usuarioLivros; }
    public void setUsuarioLivros(List<UsuarioLivro> usuarioLivros) { this.usuarioLivros = usuarioLivros; }
    public String getNmUsuario() { return nmUsuario; }
    public void setNmUsuario(String nmUsuario) { this.nmUsuario = nmUsuario; }
    public String getCPF() { return CPF; }
    public void setCPF(String cPF) { CPF = cPF; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getFoto() { return foto; }
    public void setFoto(MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            FileStorageServiceUsuario fs = new FileStorageServiceUsuario();
            this.foto = fs.armazenarArquivoUsuario(foto);
        }
    }
    public void setFoto(String foto) { this.foto = foto; }
    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public int getCdUsuario() { return cdUsuario; }
    public void setCdUsuario(int cdUsuario) { this.cdUsuario = cdUsuario; }
    public double getAvaliacao() { return avaliacao; }
    public void setAvaliacao(double avaliacao) { this.avaliacao = avaliacao; }
}