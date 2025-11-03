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

    public int getCdCategoria() {
        return cdCategoria;
    }

    public void setCdCategoria(int cdCategoria) {
        this.cdCategoria = cdCategoria;
    }

    public String getNmCategoria() {
        return nmCategoria;
    }

    public void setNmCategoria(String nmCategoria) {
        this.nmCategoria = nmCategoria;
    }

    public List<LivroCategoria> getLivroCategoria() {
        return this.livroCategoria == null ? null: new ArrayList<>(this.livroCategoria);
    }

    public void setLivroCategoria(List<LivroCategoria> livroCategoria) {
        this.livroCategoria = livroCategoria == null ? null: new ArrayList<>(livroCategoria);
    }
}
