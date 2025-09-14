package com.trocabook.Trocabook.model.dto;

public class LogAdminDTO {
    private String operacao;

    private int cdUsuario;

    private int adminResponsavelId;

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public int getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(int cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public int getAdminResponsavelId() {
        return adminResponsavelId;
    }

    public void setAdminResponsavelId(int adminResponsavelId) {
        this.adminResponsavelId = adminResponsavelId;
    }
}
