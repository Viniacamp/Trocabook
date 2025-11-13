package com.trocabook.Trocabook.model.dto;

public class MyMemoryResponse {
    private ResponseData responseData;

    // Construtor vazio para Jackson/Frameworks
    public MyMemoryResponse() {}

    // -----------------------------------------------------------------
    // CLASSE ANINHADA: ResponseData
    // -----------------------------------------------------------------
    public static class ResponseData {
        private String translatedText;

        // Construtor vazio para Jackson/Frameworks
        public ResponseData() {}

        // --- CONSTRUTOR DE CÓPIA (A chave da solução) ---
        // Usado pelos getters/setters da classe externa
        public ResponseData(ResponseData outro) {
            if (outro != null) {
                // String é imutável, cópia direta é segura
                this.translatedText = outro.translatedText;
            }
        }

        // Getters e Setters (Strings são seguros)
        public String getTranslatedText() {
            return translatedText;
        }

        public void setTranslatedText(String translatedText) {
            this.translatedText = translatedText;
        }
    }

    // -----------------------------------------------------------------
    // MÉTODOS EXTERNOS (Corrigidos com Cópia Defensiva)
    // -----------------------------------------------------------------

    /**
     * CORRIGIDO: Retorna uma cópia defensiva do objeto.
     */
    public ResponseData getResponseData() {
        // Retorna uma *nova* instância, não a referência interna
        return (this.responseData == null) ? null : new ResponseData(this.responseData);
    }

    /**
     * CORRIGIDO: Armazena uma cópia defensiva do objeto.
     */
    public void setResponseData(ResponseData responseData) {
        // Armazena uma *nova* instância, não a referência externa
        this.responseData = (responseData == null) ? null : new ResponseData(responseData);
    }
}