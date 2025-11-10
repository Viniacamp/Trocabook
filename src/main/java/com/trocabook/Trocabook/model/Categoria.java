package com.trocabook.Trocabook.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdCategoria;

    private String nmCategoria;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<LivroCategoria> livroCategoria;

    public Categoria() {

    }

    public Categoria(String nmCategoria) {
        this.nmCategoria = nmCategoria;
    }

    // --- CONSTRUTOR DE CÓPIA (ADICIONADO) ---
    /**
     * Cria uma cópia defensiva de outro objeto Categoria.
     */
    public Categoria(Categoria outraCategoria) {
        if (outraCategoria == null) {
            return;
        }

        // 1. Copia de campos primitivos e imutáveis
        this.cdCategoria = outraCategoria.cdCategoria;
        this.nmCategoria = outraCategoria.nmCategoria;

        // 2. Cópia defensiva da lista (usando a mesma lógica segura
        //    que você já tinha nos seus getters/setters)
        this.livroCategoria = (outraCategoria.livroCategoria == null)
                ? null
                : new ArrayList<>(outraCategoria.livroCategoria);
    }
    // --- FIM DA ADIÇÃO ---


    public int getCdCategoria() {
        return cdCategoria;
    }

    public void setCdCategoria(int cdCategoria) {
        this.cdCategoria = cdCategoria;
    }

    public String getNmCategoria() {
        return nmCategoria; // Seguro, String é imutável
    }

    public void setNmCategoria(String nmCategoria) {
        this.nmCategoria = nmCategoria; // Seguro
    }

    // O seu código aqui já estava perfeito!
    public List<LivroCategoria> getLivroCategoria() {
        return this.livroCategoria == null ? null: new ArrayList<>(this.livroCategoria);
    }

    public void setLivroCategoria(List<LivroCategoria> livroCategoria) {
        this.livroCategoria = livroCategoria == null ? null: new ArrayList<>(livroCategoria);
    }
}