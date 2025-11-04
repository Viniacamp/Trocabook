package com.trocabook.Trocabook.service;

import com.trocabook.Trocabook.model.*;
import com.trocabook.Trocabook.model.dto.GoogleBooksResponse;
import com.trocabook.Trocabook.model.dto.LivroDTO;
import com.trocabook.Trocabook.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LivroService {


    private final LivroRepository livroRepository;


    private final UsuarioLivroRepository usuarioLivroRepository;


    private final FileStorageServiceUsuario fileStorageService;

    private final AutorRepository autorRepository;

    private final CategoriaRepository categoriaRepository;

    private final LivroAutorRepository livroAutorRepository;

    private final LivroCategoriaRepository livroCategoriaRepository;

    private final String url = "https://www.googleapis.com/books/v1/volumes?q=";

    private final RestTemplate restTemplate;



    @Autowired
    public LivroService(LivroRepository livroRepository, UsuarioLivroRepository usuarioLivroRepository,
                        FileStorageServiceUsuario fileStorageService,
                        RestTemplate restTemplate,
                        AutorRepository autorRepository, LivroCategoriaRepository livroCategoriaRepository, CategoriaRepository categoriaRepository, LivroAutorRepository livroAutorRepository) {
        this.livroRepository = livroRepository;
        this.usuarioLivroRepository = usuarioLivroRepository;
        this.fileStorageService = fileStorageService;
        this.restTemplate = restTemplate;
        this.autorRepository = autorRepository;
        this.livroCategoriaRepository = livroCategoriaRepository;
        this.categoriaRepository = categoriaRepository;
        this.livroAutorRepository = livroAutorRepository;
    }



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

    public Livro anunciarLivroApi(String titulo){
        GoogleBooksResponse respApi = this.buscarLivro(titulo);
        System.out.println(respApi);
        if (respApi == null || respApi.getItens() == null || respApi.getItens().isEmpty()) {
            return null;
        }
        System.out.println("Passei aqui");



        GoogleBooksResponse.Item primeiroItem = respApi.getItens().getFirst();
        GoogleBooksResponse.VolumeInfo info = primeiroItem.getVolumeInfo();

        // ❗ Procura se o livro já existe no banco
        Livro livroExistente = livroRepository.findByNmLivroIgnoreCase(info.getTitulo()).orElse(null);
        if (livroExistente != null) {
            return livroExistente;
        }


        // 📌 Primeiro tenta pegar a thumbnail do primeiro volume
        String capa = (info.getLinksImages() != null && info.getLinksImages().getThumbnail() != null)
                ? info.getLinksImages().getThumbnail()
                : null;

        List<String> categorias = info.getCategorias() != null ? info.getCategorias() : new ArrayList<>();

        List<String> autores = info.getAutores() != null ? info.getAutores() : new ArrayList<>();

        // 📌 Se o primeiro item não tiver capa, procura nos outros
        if (capa == null) {
            for (GoogleBooksResponse.Item item : respApi.getItens()) {
                GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
                if (v != null && v.getLinksImages() != null && v.getLinksImages().getThumbnail() != null) {
                    System.out.println(v.getLinksImages().getThumbnail());
                    capa = v.getLinksImages().getThumbnail();
                    break;
                }
            }
        }

        if (categorias.isEmpty()) {
            for (GoogleBooksResponse.Item item : respApi.getItens()) {
                GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
                if (v != null && v.getCategorias() != null && !v.getCategorias().isEmpty()) {
                    categorias = v.getCategorias();
                    break;
                }
            }
        }

        if (autores.isEmpty()) {
            for (GoogleBooksResponse.Item item : respApi.getItens()) {
                GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
                if (v != null && v.getAutores() != null && !v.getAutores().isEmpty()) {
                    autores = v.getAutores();
                    break;
                }
            }
        }

        System.out.println(autores);
        System.out.println(categorias);

        // 📌 Caso nenhum item tenha capa, usa capa padrão do sistema
        if (capa == null) {
            capa = "/img/default-capa.png";
        }



        // ✅ Cria novo livro
        Livro livro = new Livro();
        System.out.println("Capa: " + capa);
        System.out.println("Titulo: " + info.getTitulo());
        livro.setNmLivro(info.getTitulo());
        livro.setCapa(capa);
        livro.setAnoPublicacao(1980);


        // ✅ Converte diferentes tipos de datas da API
        String data = info.getDataPublicacao();
        try {
            if (data.length() == 4) livro.setDataPublicacao(LocalDate.parse(data + "-01-01"));
            else if (data.length() == 7) livro.setDataPublicacao(LocalDate.parse(data + "-01"));
            else livro.setDataPublicacao(LocalDate.parse(data));
        } catch (Exception e) {
            livro.setDataPublicacao(LocalDate.now());
        }
        System.out.println("Data: " + livro.getDataPublicacao());
        System.out.println("Passei aqui");

        livroRepository.save(livro);

        // ✅ Autores

        for (String nomeAutor : autores) {
            Autor autor = autorRepository.findByNmAutor(nomeAutor)
                    .orElseGet(() -> autorRepository.save(new Autor(nomeAutor)));

            LivroAutor rel = new LivroAutor();
            rel.setLivro(livro);
            rel.setAutor(autor);
            livroAutorRepository.save(rel);
        }


        // ✅ Categorias

        for (String nmCategoria : categorias) {
            Categoria categoria = categoriaRepository.findByNmCategoria(nmCategoria)
                    .orElseGet(() -> categoriaRepository.save(new Categoria(nmCategoria)));

            LivroCategoria rel = new LivroCategoria();
            rel.setLivro(livro);
            rel.setCategoria(categoria);
            livroCategoriaRepository.save(rel);
        }


        return livro;
    }

    public List<LivroDTO> listarLivrosApi(String titulo) {
        GoogleBooksResponse respApi = this.buscarLivro(titulo);
        System.out.println(respApi);
        if (respApi == null || respApi.getItens() == null || respApi.getItens().isEmpty()) {
            return Collections.emptyList();
        }
        List<LivroDTO> livros = new ArrayList<>();
        for (GoogleBooksResponse.Item item : respApi.getItens()) {
            GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
            if (v == null){
                continue;
            }
            if (livros.stream().anyMatch(lDto -> lDto.getTitulo().equalsIgnoreCase(v.getTitulo()))) {
                continue;
            }
            Livro livroExistente = livroRepository.findByNmLivroIgnoreCase(v.getTitulo()).orElse(null);
            if (livroExistente != null) {
                LivroDTO livroDTO = new LivroDTO(livroExistente);
                livros.add(livroDTO);
                continue;
            }
            LivroDTO livro = new LivroDTO();
            livro.setTitulo(v.getTitulo());
            livro.setAutores((v.getAutores() == null || v.getAutores().isEmpty()) ? null : v.getAutores());
            livro.setCategorias((v.getCategorias() == null || v.getCategorias().isEmpty()) ? null : v.getCategorias());
            livro.setThumbnailUrl((v.getLinksImages() == null || v.getLinksImages().getThumbnail() == null)? null : v.getLinksImages().getThumbnail());
            String data = v.getDataPublicacao();
            try {
                if (data.length() == 4) livro.setDataPublicacao(LocalDate.parse(data + "-01-01"));
                else if (data.length() == 7) livro.setDataPublicacao(LocalDate.parse(data + "-01"));
                else livro.setDataPublicacao(LocalDate.parse(data));
            } catch (Exception e) {
                livro.setDataPublicacao(LocalDate.now());
            }
            if (livro.getAutores() == null || livro.getCategorias() == null || livro.getThumbnailUrl() == null) {
                livro = buscarDadosFaltantes(livro);
            }

            livros.add(livro);

        }
        return livros;
    }

    private LivroDTO buscarDadosFaltantes(LivroDTO dto) {
        // Apenas 1 chamada extra à API
        GoogleBooksResponse resp = this.buscarLivro(dto.getTitulo());

        if (resp == null || resp.getItens() == null) return dto;

        for (GoogleBooksResponse.Item item : resp.getItens()) {
            GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
            if (v == null) continue;

            // Preencher capa se vazia
            if (dto.getThumbnailUrl() == null) {
                if (v.getLinksImages() != null && v.getLinksImages().getThumbnail() != null) {
                    dto.setThumbnailUrl(v.getLinksImages().getThumbnail());
                }
            }

            // Preencher autores se vazio
            if (dto.getAutores() == null || dto.getAutores().isEmpty()) {
                if (v.getAutores() != null && !v.getAutores().isEmpty()) {
                    dto.setAutores(v.getAutores());
                }
            }

            // Preencher categorias se vazio
            if (dto.getCategorias() == null || dto.getCategorias().isEmpty()) {
                if (v.getCategorias() != null && !v.getCategorias().isEmpty()) {
                    dto.setCategorias(v.getCategorias());
                }
            }

            // Se tudo foi encontrado, pode parar
            if (dto.getThumbnailUrl() != null &&
                    dto.getAutores() != null && !dto.getAutores().isEmpty() &&
                    dto.getCategorias() != null && !dto.getCategorias().isEmpty())
            {
                return dto;
            }
        }
        return dto;
    }



    public GoogleBooksResponse buscarLivro(String titulo){
        System.out.println(url + titulo);
        ResponseEntity<GoogleBooksResponse> resposta = restTemplate.exchange(
                url + titulo,
                HttpMethod.GET,
                null,
                GoogleBooksResponse.class

        );
        if (resposta.getBody().getItens() != null){
            System.out.println("Itens retornados: " + resposta.getBody().getItens().size());
        } else {
            System.out.println("Deu ruim");
        }
        return resposta.getBody();
    }
}