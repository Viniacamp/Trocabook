package com.trocabook.Trocabook.model;

import jakarta.persistence.*;

@Entity
public class LivroCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdLivroCategoria;

    @ManyToOne
    @JoinColumn(name = "cdLivro", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "cdCategoria", nullable = false)
    private Categoria categoria;

    public int getCdLivroCategoria() {
        return cdLivroCategoria;
    }

    public void setCdLivroCategoria(int cdLivroCategoria) {
        this.cdLivroCategoria = cdLivroCategoria;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
