package com.trocabook.Trocabook.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList; // --- ADICIONADO --- (Necessário para new ArrayList<>() )
import java.util.List;

public class GoogleBooksResponse {
    @JsonProperty("items")
    private List<Item> itens;

    // --- CONSTRUTORES (Boa prática) ---
    public GoogleBooksResponse() {}

    public GoogleBooksResponse(GoogleBooksResponse other) {
        if (other == null) return;
        // Chama a cópia defensiva "profunda"
        this.setItens(other.itens);
    }

    // ----------------- CLASSE ITEM -----------------

    public static class Item {
        @JsonProperty("volumeInfo")
        private VolumeInfo volumeInfo;

        // --- CONSTRUTORES (Boa prática) ---
        public Item() {}

        // --- ADICIONADO --- (Construtor de Cópia)
        public Item(Item other) {
            if (other == null) return;
            // Chama o construtor de cópia do VolumeInfo
            this.volumeInfo = (other.volumeInfo == null) ? null : new VolumeInfo(other.volumeInfo);
        }

        // --- CORRIGIDO ---
        public VolumeInfo getVolumeInfo() {
            // Retorna uma CÓPIA, não a referência
            return (this.volumeInfo == null) ? null : new VolumeInfo(this.volumeInfo);
        }

        // --- CORRIGIDO ---
        public void setVolumeInfo(VolumeInfo volumeInfo) {
            // Armazena uma CÓPIA, não a referência
            this.volumeInfo = (volumeInfo == null) ? null : new VolumeInfo(volumeInfo);
        }
    }

    // ----------------- CLASSE VOLUMEINFO -----------------

    public static class VolumeInfo {
        @JsonProperty("title")
        private String titulo;
        @JsonProperty("publishedDate")
        private String dataPublicacao;
        @JsonProperty("authors")
        private List<String> autores;
        @JsonProperty("categories")
        private List<String> categorias;
        @JsonProperty("imageLinks")
        private LinksImage linksImages;

        // --- CONSTRUTORES (Boa prática) ---
        public VolumeInfo() {}

        // --- ADICIONADO --- (Construtor de Cópia)
        public VolumeInfo(VolumeInfo other) {
            if (other == null) return;

            // 1. Copia de campos imutáveis (String)
            this.titulo = other.titulo;
            this.dataPublicacao = other.dataPublicacao;

            // 2. Cópia defensiva de campos mutáveis (Listas)
            this.autores = (other.autores == null) ? null : new ArrayList<>(other.autores);
            this.categorias = (other.categorias == null) ? null : new ArrayList<>(other.categorias);

            // 3. Cópia defensiva de objeto mutável (LinksImage)
            this.linksImages = (other.linksImages == null) ? null : new LinksImage(other.linksImages);
        }

        // --- Getters/Setters de campos imutáveis (Seguros) ---
        public String getTitulo() {
            return titulo;
        }
        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }
        public String getDataPublicacao() {
            return dataPublicacao;
        }
        public void setDataPublicacao(String dataPublicacao) {
            this.dataPublicacao = dataPublicacao;
        }

        // --- CORRIGIDO --- (Getters/Setters de campos mutáveis)
        public List<String> getAutores() {
            // Retorna CÓPIA da lista
            return (this.autores == null) ? null : new ArrayList<>(this.autores);
        }
        public void setAutores(List<String> autores) {
            // Armazena CÓPIA da lista
            this.autores = (autores == null) ? null : new ArrayList<>(autores);
        }

        // --- CORRIGIDO --- (Getters/Setters de campos mutáveis)
        public List<String> getCategorias() {
            // Retorna CÓPIA da lista
            return (this.categorias == null) ? null : new ArrayList<>(this.categorias);
        }
        public void setCategorias(List<String> categorias) {
            // Armazena CÓPIA da lista
            this.categorias = (categorias == null) ? null : new ArrayList<>(categorias);
        }

        // --- O SEU CÓDIGO CORRETO (Já estava certo) ---
        public LinksImage getLinksImages() {
            return (this.linksImages == null) ? null : new LinksImage(this.linksImages);
        }
        public void setLinksImages(LinksImage linksImages) {
            this.linksImages = (linksImages == null) ? null : new LinksImage(linksImages);
        }
    }

    // ----------------- CLASSE LINKSIMAGE -----------------

    public static class LinksImage {
        private String thumbnail;

        // (Seu código já estava correto)
        public LinksImage() { }

        public LinksImage(LinksImage other) {
            if (other != null) {
                this.thumbnail = other.thumbnail;
            }
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    // ----------------- GETTERS/SETTERS DE GoogleBooksResponse -----------------



    // --- CORRIGIDO --- (Cópia "Profunda" / Deep Copy)
    public List<Item> getItens() {
        if (this.itens == null) {
            return null;
        }
        // Não basta copiar a lista, temos que copiar CADA item DENTRO da lista
        List<Item> copiaLista = new ArrayList<>();
        for (Item item : this.itens) {
            copiaLista.add(new Item(item)); // Usa o construtor de cópia do Item
        }
        return copiaLista;
    }

    // --- CORRIGIDO --- (Cópia "Profunda" / Deep Copy)
    public void setItens(List<Item> itens) {
        if (itens == null) {
            this.itens = null;
            return;
        }
        // Não basta copiar a lista, temos que copiar CADA item DENTRO da lista
        this.itens = new ArrayList<>();
        for (Item item : itens) {
            this.itens.add(new Item(item)); // Usa o construtor de cópia do Item
        }
    }
}