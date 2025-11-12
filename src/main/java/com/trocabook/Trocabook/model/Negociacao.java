package com.trocabook.Trocabook.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.validation.Valid;

@Entity
public class Negociacao {
    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdNegociacao;

    @ManyToOne
    @JoinColumn(name = "cd_usuarioAnunciante", nullable = false)
    private Usuario usuarioAnunciante;

    @ManyToOne
    @JoinColumn(name = "cd_usuarioInteressado", nullable = false)
    private Usuario usuarioInteressado;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dtNegociacao;

    @ManyToOne
    @JoinColumn(name = "cd_livro", nullable = false)
    private Livro livro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Selecione o Tipo da Resolução da Negociação")
    private Tipo tipo;

    public int getCdNegociacao() {
        return cdNegociacao;
    }

    public void setCdNegociacao(int cd_negociacao) {
        this.cdNegociacao = cd_negociacao;
    }

    // --- CORRIGIDO ---
    public Usuario getUsuarioAnunciante() {
        // Retorna uma CÓPIA, não a referência interna
        return (this.usuarioAnunciante == null) ? null : new Usuario(this.usuarioAnunciante);
    }

    // --- CORRIGIDO ---
    public void setUsuarioAnunciante(Usuario usuarioAnunciante) {
        // Armazena uma CÓPIA, não a referência externa
        this.usuarioAnunciante = (usuarioAnunciante == null) ? null : new Usuario(usuarioAnunciante);
    }

    // --- CORRIGIDO ---
    public Usuario getUsuarioInteressado() {
        // Retorna uma CÓPIA, não a referência interna
        return (this.usuarioInteressado == null) ? null : new Usuario(this.usuarioInteressado);
    }

    // --- CORRIGIDO ---
    public void setUsuarioInteressado(Usuario usuarioInteressado) {
        // Armazena uma CÓPIA, não a referência externa
        this.usuarioInteressado = (usuarioInteressado == null) ? null : new Usuario(usuarioInteressado);
    }

    public LocalDateTime getDtNegociacao() {
        return dtNegociacao; // Seguro, LocalDateTime é IMUTÁVEL
    }

    public void setDtNegociacao(LocalDateTime dtNegociacao) {
        this.dtNegociacao = dtNegociacao; // Seguro, LocalDateTime é IMUTÁVEL
    }

    // --- CORRIGIDO ---
    public Livro getLivro() {
        // Retorna uma CÓPIA, não a referência interna
        return (this.livro == null) ? null : new Livro(this.livro);
    }

    // --- CORRIGIDO ---
    public void setLivro(Livro livro) {
        // Armazena uma CÓPIA, não a referência externa
        this.livro = (livro == null) ? null : new Livro(livro);
    }

    public Tipo getTipo() {
        return tipo; // Seguro, Enum é IMUTÁVEL
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo; // Seguro, Enum é IMUTÁVEL
    }

    public enum Tipo{
        TROCA, VENDA, AMBOS
    }
}