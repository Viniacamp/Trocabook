package com.trocabook.Trocabook.model.dto;

public class MensagemDTO {
    private int cdUsuarioRemetente;
    private int cdUsuarioDestinatario;
    private int cdUsuarioLivro;
    private String conteudo;



    public int getCdUsuarioRemetente() {
        return cdUsuarioRemetente;
    }

    public void setCdUsuarioRemetente(int cdUsuarioRemente) {
        this.cdUsuarioRemetente = cdUsuarioRemente;
    }

    public int getCdUsuarioDestinatario() {
        return cdUsuarioDestinatario;
    }

    public void setCdUsuarioDestinatario(int cdUsuarioDestinatario) {
        this.cdUsuarioDestinatario = cdUsuarioDestinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getCdUsuarioLivro() {
        return cdUsuarioLivro;
    }

    public void setCdUsuarioLivro(int cdUsuarioLivro) {
        this.cdUsuarioLivro = cdUsuarioLivro;
    }
}

