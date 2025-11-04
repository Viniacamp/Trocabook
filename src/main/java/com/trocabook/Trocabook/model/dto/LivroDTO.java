package com.trocabook.Trocabook.model.dto;

import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.LivroAutor;
import com.trocabook.Trocabook.model.LivroCategoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroDTO {
    private String titulo;
    private List<String> autores;
    private List<String> categorias;
    private LocalDate dataPublicacao;
    private String thumbnailUrl;

    public LivroDTO(){}

    public LivroDTO(Livro livro){
        this.titulo = livro.getNmLivro();
        this.thumbnailUrl = livro.getCapa();
        this.dataPublicacao = livro.getDataPublicacao();
        this.autores = new ArrayList<>();
        this.categorias = new ArrayList<>();
        List<LivroAutor> livroAutores = livro.getLivroAutor();
        for (LivroAutor livroAutor : livroAutores) {
            this.autores.add(livroAutor.getAutor().getNmAutor());
        }
        List<LivroCategoria> livroCategorias = livro.getLivroCategoria();
        for (LivroCategoria livroCategoria : livroCategorias) {
            this.categorias.add(livroCategoria.getCategoria().getNmCategoria());
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getAutores() {
        return this.autores == null ? null : new ArrayList<>(this.autores);
    }

    public void setAutores(List<String> autores) {
        this.autores = autores == null ? null : new ArrayList<>(autores);
    }

    public List<String> getCategorias() {
        return categorias == null ? null : new ArrayList<>(this.categorias);
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias == null ? null : new ArrayList<>(categorias);
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
