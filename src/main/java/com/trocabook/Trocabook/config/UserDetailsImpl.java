package com.trocabook.Trocabook.config;

import com.trocabook.Trocabook.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario; // Esta será a nossa cópia segura

    // --- CONSTRUTOR CORRIGIDO ---
    public UserDetailsImpl(Usuario usuarioOriginal) {
        // Em vez de guardar a referência, criamos uma cópia segura com os dados essenciais.
        this.usuario = new Usuario();
        this.usuario.setCdUsuario(usuarioOriginal.getCdUsuario());
        this.usuario.setEmail(usuarioOriginal.getEmail());
        this.usuario.setSenha(usuarioOriginal.getSenha());
        this.usuario.setStatus(usuarioOriginal.getStatus());
        this.usuario.setNmUsuario(usuarioOriginal.getNmUsuario());
        this.usuario.setFoto(usuarioOriginal.getFoto());
        // Não copiamos as listas (negociacoes, etc.), pois não são necessárias para a autenticação.
    }

    // --- GETTER CORRIGIDO ---
    public Usuario getUsuario() {
        // Devolvemos outra cópia para garantir que o estado interno da classe nunca seja exposto.
        Usuario copiaDeRetorno = new Usuario();
        copiaDeRetorno.setCdUsuario(this.usuario.getCdUsuario());
        copiaDeRetorno.setEmail(this.usuario.getEmail());
        // Não retornamos a senha na cópia por segurança
        copiaDeRetorno.setStatus(this.usuario.getStatus());
        copiaDeRetorno.setNmUsuario(this.usuario.getNmUsuario());
        copiaDeRetorno.setFoto(this.usuario.getFoto());
        return copiaDeRetorno;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return this.usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.usuario.getStatus() == 'A';
    }
}