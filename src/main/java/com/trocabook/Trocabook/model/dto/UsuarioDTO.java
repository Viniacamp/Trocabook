package com.trocabook.Trocabook.model.dto;

public class UsuarioDTO {
    private int id;
    private String nome;
    private String foto;
    private double avaliacao;
    private char status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


}
