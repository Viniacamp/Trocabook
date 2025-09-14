package com.trocabook.Trocabook.model.dto;

import java.time.LocalDate;

public class DashboardDTO {
    private Long totalUsuarios;
    private Long totalLivros;
    private Long totalNegociacao;

    public Long getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(Long totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public Long getTotalLivros() {
        return totalLivros;
    }

    public void setTotalLivros(Long totalLivros) {
        this.totalLivros = totalLivros;
    }

    public Long getTotalNegociacao() {
        return totalNegociacao;
    }

    public void setTotalNegociacao(Long totalNegociacao) {
        this.totalNegociacao = totalNegociacao;
    }

}
