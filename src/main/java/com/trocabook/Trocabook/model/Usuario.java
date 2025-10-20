package com.trocabook.Trocabook.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.ArrayList; // Importação adicionada
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

    @Column(nullable = false)
    private String senha;

    private String foto; // O campo foto agora é apenas uma String

    @NotNull
    @Column(nullable = false)
    private char status;

    @Max(5)
    @Column(nullable = false)
    private double avaliacao;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "reset_password_token_expiry_date")
    private LocalDateTime resetPasswordTokenExpiryDate;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioLivro> usuarioLivros;

    @OneToMany(mappedBy = "usuarioAnunciante", cascade = CascadeType.ALL)
    private List<Negociacao> negociacoesAnunciante;

    @OneToMany(mappedBy = "usuarioInteressado", cascade = CascadeType.ALL)
    private List<Negociacao> negociacoesInteressado;

    // --- Getters e Setters Corrigidos ---

    public List<Negociacao> getNegociacoesAnunciante() {
        // Retorna uma cópia para proteger a lista original
        return this.negociacoesAnunciante == null ? null : new ArrayList<>(this.negociacoesAnunciante);
    }
    public void setNegociacoesAnunciante(List<Negociacao> negociacoesAnunciante) {
        // Armazena uma cópia para que alterações externas não afetem a entidade
        this.negociacoesAnunciante = negociacoesAnunciante == null ? null : new ArrayList<>(negociacoesAnunciante);
    }
    public List<Negociacao> getNegociacoesInteressado() {
        return this.negociacoesInteressado == null ? null : new ArrayList<>(this.negociacoesInteressado);
    }
    public void setNegociacoesInteressado(List<Negociacao> negociacoesInteressado) {
        this.negociacoesInteressado = negociacoesInteressado == null ? null : new ArrayList<>(negociacoesInteressado);
    }
    public List<UsuarioLivro> getUsuarioLivros() {
        return this.usuarioLivros == null ? null : new ArrayList<>(this.usuarioLivros);
    }
    public void setUsuarioLivros(List<UsuarioLivro> usuarioLivros) {
        this.usuarioLivros = usuarioLivros == null ? null : new ArrayList<>(usuarioLivros);
    }

    // --- Getters e Setters que permanecem os mesmos ---

    public String getNmUsuario() { return nmUsuario; }
    public void setNmUsuario(String nmUsuario) { this.nmUsuario = nmUsuario; }
    public String getCPF() { return CPF; }
    public void setCPF(String cPF) { CPF = cPF; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getFoto() { return foto; }

    // O setFoto agora apenas aceita a String com o caminho do arquivo
    public void setFoto(String foto) { this.foto = foto; }

    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public int getCdUsuario() { return cdUsuario; }
    public void setCdUsuario(int cdUsuario) { this.cdUsuario = cdUsuario; }
    public double getAvaliacao() { return avaliacao; }
    public void setAvaliacao(double avaliacao) { this.avaliacao = avaliacao; }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public LocalDateTime getResetPasswordTokenExpiryDate() {
        return resetPasswordTokenExpiryDate;
    }

    public void setResetPasswordTokenExpiryDate(LocalDateTime resetPasswordTokenExpiryDate) {
        this.resetPasswordTokenExpiryDate = resetPasswordTokenExpiryDate;
    }
}