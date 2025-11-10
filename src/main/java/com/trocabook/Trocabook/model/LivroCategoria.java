package com.trocabook.Trocabook.model;

import jakarta.persistence.*; // (Seus imports)

@Entity
public class LivroCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdLivroCategoria;

    @ManyToOne
    @JoinColumn(name = "cd_livro", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "cd_categoria", nullable = false)
    private Categoria categoria;

    // --- Getters e Setters de ID (Seguros) ---
    public int getCdLivroCategoria() {
        return cdLivroCategoria;
    }
    public void setCdLivroCategoria(int cdLivroCategoria) {
        this.cdLivroCategoria = cdLivroCategoria;
    }

    // --- Getters e Setters de Livro (Corrigidos) ---
    public Livro getLivro() {
        // CORREÇÃO: Retorna uma CÓPIA
        return (this.livro == null) ? null : new Livro(this.livro);
    }
    public void setLivro(Livro livro) {
        // CORREÇÃO: Armazena uma CÓPIA
        this.livro = (livro == null) ? null : new Livro(livro);
    }

    // --- Getters e Setters de Categoria (Corrigidos) ---
    public Categoria getCategoria() {
        // CORREÇÃO: Retorna uma CÓPIA
        return (this.categoria == null) ? null : new Categoria(this.categoria);
    }
    public void setCategoria(Categoria categoria) {
        // CORREÇÃO: Armazena uma CÓPIA
        this.categoria = (categoria == null) ? null : new Categoria(categoria);
    }
}