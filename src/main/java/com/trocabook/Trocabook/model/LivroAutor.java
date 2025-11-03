package com.trocabook.Trocabook.model;

import jakarta.persistence.*;

@Entity
public class LivroAutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdLivroAutor;

    @ManyToOne
    @JoinColumn(name = "cdLivro", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "cdAutor", nullable = false)
    private Autor autor;

    public int getCdLivroAutor() {
        return cdLivroAutor;
    }

    public void setCdLivroAutor(int cdLivroAutor) {
        this.cdLivroAutor = cdLivroAutor;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
