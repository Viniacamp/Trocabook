package com.trocabook.Trocabook.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPublicacao;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<LivroAutor> livroAutor;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<LivroCategoria> livroCategoria;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<UsuarioLivro> usuarioLivros;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<Negociacao> negociacoes;

    // --- CONSTRUTORES ---

    /**
     * Construtor padrão (vazio).
     * Bom para JPA, Jackson e outras bibliotecas.
     */
    public Livro() {
    }

    /**
     * CONSTRUTOR DE CÓPIA (A SOLUÇÃO)
     * Cria uma cópia defensiva de outro objeto Livro.
     */
    public Livro(Livro outroLivro) {
        if (outroLivro == null) {
            return;
        }

        // Copia de campos imutáveis/primitivos
        this.cdLivro = outroLivro.cdLivro;
        this.nmLivro = outroLivro.nmLivro;
        this.anoPublicacao = outroLivro.anoPublicacao;
        this.capa = outroLivro.capa;
        this.dataPublicacao = outroLivro.dataPublicacao;

        // Copia defensiva das listas (objetos mutáveis)
        this.livroAutor = (outroLivro.livroAutor == null) ? null : new ArrayList<>(outroLivro.livroAutor);
        this.livroCategoria = (outroLivro.livroCategoria == null) ? null : new ArrayList<>(outroLivro.livroCategoria);
        this.usuarioLivros = (outroLivro.usuarioLivros == null) ? null : new ArrayList<>(outroLivro.usuarioLivros);
        this.negociacoes = (outroLivro.negociacoes == null) ? null : new ArrayList<>(outroLivro.negociacoes);
    }


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

    public List<LivroCategoria> getLivroCategoria() {
        return this.livroCategoria == null ? null : new ArrayList<>(this.livroCategoria);
    }

    public void setLivroCategoria(List<LivroCategoria> livroCategoria) {
        this.livroCategoria = livroCategoria == null ? null : new ArrayList<>(livroCategoria);
    }

    public List<LivroAutor> getLivroAutor() {
        return this.livroAutor == null ? null : new ArrayList<>(this.livroAutor);
    }

    public void setLivroAutor(List<LivroAutor> livroAutor) {
        this.livroAutor = livroAutor == null ? null : new ArrayList<>(livroAutor);
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

    public LocalDate getDataPublicacao() {
        return dataPublicacao; // Seguro, LocalDate é IMUTÁVEL
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao; // Seguro, LocalDate é IMUTÁVEL
    }
}