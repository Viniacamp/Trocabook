package com.trocabook.Trocabook.service;

import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.LivroRepository;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioLivroRepository usuarioLivroRepository;

    @Autowired
    private FileStorageServiceUsuario fileStorageService;

    public Livro cadastrarNovoLivro(String nmLivro, Integer anoPublicacao, MultipartFile capaArquivo) throws IOException {
        String caminhoDaCapa = fileStorageService.armazenarArquivoLivro(capaArquivo);

        Livro novoLivro = new Livro();
        novoLivro.setNmLivro(nmLivro);
        novoLivro.setAnoPublicacao(anoPublicacao);
        novoLivro.setCapa(caminhoDaCapa);

        return livroRepository.save(novoLivro);
    }

    public void anunciarNovoLivro(Livro livro, MultipartFile capaArquivo, Usuario usuario, String tipoNegociacao) throws IOException {
        // 1. Salva o arquivo da capa e obtém o caminho (String)
        String caminhoDaCapa = fileStorageService.armazenarArquivoLivro(capaArquivo);
        livro.setCapa(caminhoDaCapa);

        // 2. Salva a entidade Livro no banco de dados
        livroRepository.save(livro);

        // 3. Cria a relação entre o usuário e o livro
        UsuarioLivro usuarioLivro = new UsuarioLivro();
        usuarioLivro.setUsuario(usuario);
        usuarioLivro.setLivro(livro);
        usuarioLivro.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf(tipoNegociacao));

        // 4. Salva a relação no banco de dados
        usuarioLivroRepository.save(usuarioLivro);
    }
}