package com.trocabook.Trocabook.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Entity
public class UsuarioLivro {
    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdUsuarioLivro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Selecione o tipo de negociação")
    private TipoNegociacao tipoNegociacao;

    @ManyToOne
    @JoinColumn(name = "cd_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cd_livro", nullable = false)
    private Livro livro;

    public int getCdUsuarioLivro() {
        return cdUsuarioLivro;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Livro getLivro() {
        return livro;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    public void setCdUsuarioLivro(int cdUsuarioLivro) {
        this.cdUsuarioLivro = cdUsuarioLivro;
    }
    public enum TipoNegociacao {
        TROCA, VENDA, AMBOS
    }
    public TipoNegociacao getTipoNegociacao() {
        return tipoNegociacao;
    }

    public void setTipoNegociacao(TipoNegociacao tipoNegociacao) {
        this.tipoNegociacao = tipoNegociacao;
    }
}