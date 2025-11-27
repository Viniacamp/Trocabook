package com.trocabook.Trocabook.model; // (O seu package)

import jakarta.persistence.*; // (Seus imports)
import jakarta.validation.constraints.NotNull;

@Entity // (Assumindo que esta é uma entidade)
public class UsuarioLivro {

    @Id // (Assumindo a ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdUsuarioLivro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Selecione o tipo de negociação")
    private TipoNegociacao tipoNegociacao;

    @ManyToOne
    @JoinColumn(name = "cd_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cd_livro", nullable = false)
    private Livro livro;

    // --- Métodos Corrigidos ---

    public Usuario getUsuario() {
        // Retorna uma CÓPIA, não a referência interna
        return (this.usuario == null) ? null : new Usuario(this.usuario);
    }

    public void setUsuario(Usuario usuario) {
        // Armazena uma CÓPIA, não a referência externa
        this.usuario = (usuario == null) ? null : new Usuario(usuario);
    }

    public Livro getLivro() {
        // Retorna uma CÓPIA, não a referência interna
        return (this.livro == null) ? null : new Livro(this.livro);
    }

    public void setLivro(Livro livro) {
        // Armazena uma CÓPIA, não a referência externa
        this.livro = (livro == null) ? null : new Livro(livro);
    }

    // --- Métodos Seguros (Tipos Primitivos e Enums) ---

    public int getCdUsuarioLivro() {
        return cdUsuarioLivro;
    }

    public void setCdUsuarioLivro(int cdUsuarioLivro) {
        this.cdUsuarioLivro = cdUsuarioLivro;
    }

    public TipoNegociacao getTipoNegociacao() {
        return tipoNegociacao;
    }

    public void setTipoNegociacao(TipoNegociacao tipoNegociacao) {
        this.tipoNegociacao = tipoNegociacao;
    }

    // Enum (é imutável e seguro)
    public enum TipoNegociacao {
        TROCA, VENDA, AMBOS
    }
}