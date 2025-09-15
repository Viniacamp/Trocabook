package com.trocabook.Trocabook.service;

import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.dto.DashboardDTO;
import com.trocabook.Trocabook.model.dto.UsuarioCadastroDTO;
import com.trocabook.Trocabook.model.dto.UsuarioDTO;

import java.io.IOException;
import java.util.List;

public interface IDadosService {
    long obterTotalUsuarios();
    long obterTotalLivros();
    long obterTotalNegociacoes();
    void deletarUsuario(int id);
    UsuarioDTO atualizarUsuario(int id, UsuarioDTO usuarioDTO);
    DashboardDTO obterDashboard();
    Usuario obterUsuario(int id);
    List<UsuarioDTO> listarUsuarios();
}
