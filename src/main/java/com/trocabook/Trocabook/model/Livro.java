package com.trocabook.Trocabook.model;

import java.util.ArrayList;
import java.util.List;

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
    private List<UsuarioLivro> usuarioLivros;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<Negociacao> negociacoes;

    // --- Getters e Setters Corrigidos para as Listas ---

    public List<UsuarioLivro> getUsuarioLivros() {
        return this.usuarioLivros == null ? null : new ArrayList<>(this.usuarioLivros);
    }

    public void setUsuarioLivros(List<UsuarioLivro> usuarioLivros) {
        this.usuarioLivros = usuarioLivros == null ? null : new ArrayList<>(usuarioLivros);
    }

    public List<Negociacao> getNegociacoes() {
        return this.negociacoes == null ? null : new ArrayList<>(this.negociacoes);
    }

    public void setNegociacoes(List<Negociacao> negociacoes) {
        this.negociacoes = negociacoes == null ? null : new ArrayList<>(negociacoes);
    }

    // --- Outros Getters e Setters ---

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
    // O método setCapa(MultipartFile capa) foi removido.
}