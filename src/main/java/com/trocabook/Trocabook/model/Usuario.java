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
import java.util.ArrayList;
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

    private String foto;

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

    // --- CONSTRUTORES ADICIONADOS ---

    /**
     * Construtor padrão (vazio).
     * Necessário para o funcionamento do JPA/Hibernate.
     */
    public Usuario() {
    }

    /**
     * CONSTRUTOR DE CÓPIA (A SOLUÇÃO)
     * Cria uma cópia defensiva de outro objeto Usuario.
     * Isso permite que a classe Negociacao armazene cópias seguras.
     */
    public Usuario(Usuario outroUsuario) {
        if (outroUsuario == null) {
            return;
        }

        // 1. Copia de campos primitivos e imutáveis
        // (int, String, char, double, LocalDateTime são todos seguros)
        this.cdUsuario = outroUsuario.cdUsuario;
        this.nmUsuario = outroUsuario.nmUsuario;
        this.CPF = outroUsuario.CPF;
        this.email = outroUsuario.email;
        this.senha = outroUsuario.senha; // A senha também é copiada
        this.foto = outroUsuario.foto;
        this.status = outroUsuario.status;
        this.avaliacao = outroUsuario.avaliacao;
        this.resetPasswordToken = outroUsuario.resetPasswordToken;
        this.resetPasswordTokenExpiryDate = outroUsuario.resetPasswordTokenExpiryDate;

        // 2. Cópia defensiva de campos mutáveis (as Listas)
        // (Usando a mesma lógica segura que você já aplicou nos getters/setters)
        this.usuarioLivros = (outroUsuario.usuarioLivros == null) ? null : new ArrayList<>(outroUsuario.usuarioLivros);
        this.negociacoesAnunciante = (outroUsuario.negociacoesAnunciante == null) ? null : new ArrayList<>(outroUsuario.negociacoesAnunciante);
        this.negociacoesInteressado = (outroUsuario.negociacoesInteressado == null) ? null : new ArrayList<>(outroUsuario.negociacoesInteressado);
    }

    // --- Getters e Setters (Seu código original, que já estava correto) ---

    public List<Negociacao> getNegociacoesAnunciante() {
        return this.negociacoesAnunciante == null ? null : new ArrayList<>(this.negociacoesAnunciante);
    }
    public void setNegociacoesAnunciante(List<Negociacao> negociacoesAnunciante) {
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

    // --- Outros Getters e Setters (imutáveis) ---

    public String getNmUsuario() { return nmUsuario; }
    public void setNmUsuario(String nmUsuario) { this.nmUsuario = nmUsuario; }
    public String getCPF() { return CPF; }
    public void setCPF(String cPF) { CPF = cPF; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getFoto() { return foto; }
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
        return resetPasswordTokenExpiryDate; // Seguro, LocalDateTime é IMUTÁVEL
    }
    public void setResetPasswordTokenExpiryDate(LocalDateTime resetPasswordTokenExpiryDate) {
        this.resetPasswordTokenExpiryDate = resetPasswordTokenExpiryDate; // Seguro
    }
}