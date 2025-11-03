package com.trocabook.Trocabook.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdAutor;

    private String nmAutor;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<LivroAutor> livroAutor;

    public Autor() {

    }

    public Autor(String nmAutor) {
        this.nmAutor = nmAutor;
    }

    public int getCdAutor() {
        return cdAutor;
    }

    public void setCdAutor(int cdAutor) {
        this.cdAutor = cdAutor;
    }

    public String getNmAutor() {
        return nmAutor;
    }

    public void setNmAutor(String nmAutor) {
        this.nmAutor = nmAutor;
    }

    public List<LivroAutor> getLivroAutor() {
        return this.livroAutor == null ? null: new ArrayList<>(this.livroAutor);
    }

    public void setLivroAutor(List<LivroAutor> livroAutor) {
        this.livroAutor = livroAutor == null ? null: new ArrayList<>(livroAutor);
    }
}
