package com.trocabook.Trocabook.model.dto;

public class TraducaoResponse {
    private String translatedText;

    public TraducaoResponse() {}

    public TraducaoResponse(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getTranslatedText() { return translatedText; }
    public void setTranslatedText(String translatedText) { this.translatedText = translatedText; }
}