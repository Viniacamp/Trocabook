package com.trocabook.Trocabook.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdCategoria;

    private String nmCategoriaOriginal;

    private String nmCategoriaTraduzida;



    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<LivroCategoria> livroCategoria;

    public Categoria() {

    }

    public Categoria(String nmCategoriaOriginal, String nmCategoriaTraduzida) {

        this.nmCategoriaOriginal = nmCategoriaOriginal;
        this.nmCategoriaTraduzida = nmCategoriaTraduzida;
    }

    public int getCdCategoria() {
        return cdCategoria;
    }

    public void setCdCategoria(int cdCategoria) {
        this.cdCategoria = cdCategoria;
    }

    public String getNmCategoriaOriginal() {
        return nmCategoriaOriginal;
    }

    public void setNmCategoriaOriginal(String nmCategoria) {
        this.nmCategoriaOriginal = nmCategoria;
    }

    public List<LivroCategoria> getLivroCategoria() {
        return this.livroCategoria == null ? null: new ArrayList<>(this.livroCategoria);
    }

    public void setLivroCategoria(List<LivroCategoria> livroCategoria) {
        this.livroCategoria = livroCategoria == null ? null: new ArrayList<>(livroCategoria);
    }

    public String getNmCategoriaTraduzida() {
        return nmCategoriaTraduzida;
    }

    public void setNmCategoriaTraduzida(String nmCategoriaTraduzida) {
        this.nmCategoriaTraduzida = nmCategoriaTraduzida;
    }
}
