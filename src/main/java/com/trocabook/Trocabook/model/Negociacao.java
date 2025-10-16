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

    public LocalDateTime getDtNegociacao() {
        return dtNegociacao;
    }

    public void setDtNegociacao(LocalDateTime dtNegociacao) {
        this.dtNegociacao = dtNegociacao;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public enum Tipo{
        TROCA, VENDA, AMBOS
    }
}