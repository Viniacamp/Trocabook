package com.trocabook.Trocabook.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GoogleBooksResponse {
    @JsonProperty("items")
    private List<Item> itens;

    public static class Item {
        @JsonProperty("volumeInfo")
        private VolumeInfo volumeInfo;

        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(VolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }
    }

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
        @JsonProperty("language")
        private String lingua;

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

        public List<String> getAutores() {
            return autores;
        }

        public void setAutores(List<String> autores) {
            this.autores = autores;
        }

        public List<String> getCategorias() {
            return categorias;
        }

        public void setCategorias(List<String> categorias) {
            this.categorias = categorias;
        }

        public LinksImage getLinksImages() {
            return linksImages;
        }

        public void setLinksImages(LinksImage linksImages) {
            this.linksImages = linksImages;
        }

        public String getLingua() {
            return lingua;
        }

        public void setLingua(String lingua) {
            this.lingua = lingua;
        }
    }


    public static class LinksImage {
        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }




}

