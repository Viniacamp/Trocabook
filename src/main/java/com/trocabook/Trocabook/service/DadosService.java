package com.trocabook.Trocabook.service;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.dto.DashboardDTO;
import com.trocabook.Trocabook.model.dto.UsuarioCadastroDTO;
import com.trocabook.Trocabook.model.dto.UsuarioDTO;
import com.trocabook.Trocabook.repository.LivroRepository;
import com.trocabook.Trocabook.repository.NegociacaoRepository;
import com.trocabook.Trocabook.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DadosService implements IDadosService{
    final UsuarioRepository usuarioRepository;
    final LivroRepository livroRepository;
    final NegociacaoRepository negociacaoRepository;

    @Autowired
    DadosService(UsuarioRepository usuarioRepository, LivroRepository livroRepository, NegociacaoRepository negociacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
        this.negociacaoRepository = negociacaoRepository;
    }

    public long obterTotalUsuarios(){
        return usuarioRepository.count();
    }

    public long obterTotalLivros(){
        return livroRepository.count();
    }

    public long obterTotalNegociacoes(){
        return negociacaoRepository.count();
    }

    public DashboardDTO obterDashboard(){
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setTotalUsuarios(obterTotalUsuarios());
        dashboardDTO.setTotalLivros(obterTotalLivros());
        dashboardDTO.setTotalNegociacao(obterTotalNegociacoes());
        return dashboardDTO;
    }


    public void deletarUsuario(int id){

        Usuario usuario = this.obterUsuario(id);
        System.out.println(usuario);
        usuario.setStatus('I');
        usuarioRepository.save(usuario);
    }

    public UsuarioDTO atualizarUsuario(int id, UsuarioDTO usuarioDTO){
        Usuario usuario = this.obterUsuario(id);
        usuario.setAvaliacao(usuarioDTO.getAvaliacao());
        usuario.setStatus(usuarioDTO.getStatus());
        usuarioRepository.save(usuario);
        return usuarioDTO;
    }

    public Usuario obterUsuario(int id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("usuario não encontrado"));
    }

    public List<UsuarioDTO> listarUsuarios(){
        Iterable<Usuario> usuarios = usuarioRepository.findAllByStatus('A');
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getCdUsuario());
            usuarioDTO.setNome(usuario.getNmUsuario());
            usuarioDTO.setFoto(usuario.getFoto());
            usuarioDTO.setAvaliacao(usuario.getAvaliacao());
            usuarioDTO.setStatus(usuario.getStatus());
            usuarioDTOs.add(usuarioDTO);
        }
        return usuarioDTOs;


    }
}
