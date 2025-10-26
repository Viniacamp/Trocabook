package com.trocabook.Trocabook.model.dto;

public class ConversaDTO {
    private int cdUsuarioLivro;
    private int cdUsuarioDestinatario;
    private String nomeDestinatario;
    private String fotoDestinatario;
    private String ultimaMensagem;
    private boolean enviadaPeloUsuarioLogado;

    // Getters e setters
    public int getCdUsuarioLivro() { return cdUsuarioLivro; }
    public void setCdUsuarioLivro(int cdUsuarioLivro) { this.cdUsuarioLivro = cdUsuarioLivro; }

    public int getCdUsuarioDestinatario() { return cdUsuarioDestinatario; }
    public void setCdUsuarioDestinatario(int cdUsuarioDestinatario) { this.cdUsuarioDestinatario = cdUsuarioDestinatario; }

    public String getNomeDestinatario() { return nomeDestinatario; }
    public void setNomeDestinatario(String nomeDestinatario) { this.nomeDestinatario = nomeDestinatario; }

    public String getFotoDestinatario() { return fotoDestinatario; }
    public void setFotoDestinatario(String fotoDestinatario) { this.fotoDestinatario = fotoDestinatario; }

    public String getUltimaMensagem() { return ultimaMensagem; }
    public void setUltimaMensagem(String ultimaMensagem) { this.ultimaMensagem = ultimaMensagem; }

    public boolean isEnviadaPeloUsuarioLogado() { return enviadaPeloUsuarioLogado; }
    public void setEnviadaPeloUsuarioLogado(boolean enviadaPeloUsuarioLogado) { this.enviadaPeloUsuarioLogado = enviadaPeloUsuarioLogado; }
}
