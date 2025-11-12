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

    // Em LivroAutor.java

    public Livro getLivro() {
        // CORREÇÃO: Retorna uma CÓPIA, não a referência interna
        return (this.livro == null) ? null : new Livro(this.livro);
    }

    public void setLivro(Livro livro) {
        // CORREÇÃO: Armazena uma CÓPIA, não a referência externa
        this.livro = (livro == null) ? null : new Livro(livro);
    }

    public Autor getAutor() {
        // CORREÇÃO: Retorna uma cópia, não a referência interna
        return (this.autor == null) ? null : new Autor(this.autor);
    }

    public void setAutor(Autor autor) {
        // CORREÇÃO: Armazena uma cópia, não a referência externa
        this.autor = (autor == null) ? null : new Autor(autor);
    }
}
