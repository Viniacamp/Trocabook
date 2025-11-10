package com.trocabook.Trocabook.service;

import com.trocabook.Trocabook.model.*;
import com.trocabook.Trocabook.model.dto.GoogleBooksResponse;
import com.trocabook.Trocabook.model.dto.LivroDTO;
import com.trocabook.Trocabook.repository.*;
import com.trocabook.Trocabook.utils.TextoUtils;
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

    private final TraducaoService traducaoService;

    private final String url = "https://www.googleapis.com/books/v1/volumes?q=";

    private final RestTemplate restTemplate;



    @Autowired
    public LivroService(LivroRepository livroRepository, UsuarioLivroRepository usuarioLivroRepository,
                        FileStorageServiceUsuario fileStorageService,
                        RestTemplate restTemplate,
                        AutorRepository autorRepository,
                        LivroCategoriaRepository livroCategoriaRepository,
                        CategoriaRepository categoriaRepository,
                        LivroAutorRepository livroAutorRepository,
                        TraducaoService traducaoService) {
        this.livroRepository = livroRepository;
        this.usuarioLivroRepository = usuarioLivroRepository;
        this.fileStorageService = fileStorageService;
        this.restTemplate = restTemplate;
        this.autorRepository = autorRepository;
        this.livroCategoriaRepository = livroCategoriaRepository;
        this.categoriaRepository = categoriaRepository;
        this.livroAutorRepository = livroAutorRepository;
        this.traducaoService = traducaoService;
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

    public List<LivroDTO> listarLivrosApi(String titulo) {
        GoogleBooksResponse respApi = this.buscarLivro(titulo);
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
            livro.setThumbnailUrl((v.getLinksImages() == null || v.getLinksImages().getThumbnail() == null)? null : TextoUtils.garantirHttps(v.getLinksImages().getThumbnail()));
            livro.setLingua((v.getLingua() == null || v.getLingua().isEmpty()) ? null : v.getLingua());
            String data = v.getDataPublicacao();
            try {
                if (data.length() == 4) livro.setDataPublicacao(LocalDate.parse(data + "-01-01"));
                else if (data.length() == 7) livro.setDataPublicacao(LocalDate.parse(data + "-01"));
                else livro.setDataPublicacao(LocalDate.parse(data));
            } catch (Exception e) {
                livro.setDataPublicacao(LocalDate.now());
            }
            if ((livro.getAutores() == null || livro.getAutores().isEmpty()) || (livro.getCategorias() == null || livro.getCategorias().isEmpty()) || (livro.getThumbnailUrl() == null || livro.getThumbnailUrl().isBlank()) || livro.getLingua() == null) {
                livro = buscarDadosFaltantes(livro);
            }
            String linguaOriginal = livro.getLingua();
            String tituloLivro = TextoUtils.normalizar(v.getTitulo());
            boolean ehPortugues = linguaOriginal != null &&
                    (linguaOriginal.equalsIgnoreCase("pt") || linguaOriginal.equalsIgnoreCase("pt-br"));

            if (!ehPortugues) {
                livro.setTitulo(traducaoService.traduzirAPI(tituloLivro, linguaOriginal, "pt-br"));
            } else {
                livro.setTitulo(tituloLivro);
            }

            if (ehPortugues) {
                linguaOriginal = "en";
            }

            livro.setTituloFoiTraduzido(!ehPortugues);

            List<String> categorias = livro.getCategorias() != null ? livro.getCategorias() : new ArrayList<>();
            List<String> autores = livro.getAutores() != null ? livro.getAutores() : new ArrayList<>();


            List<String> categoriasTraduzidas = new ArrayList<>();

            for (String cat : categorias) {
                String categoria = TextoUtils.normalizar(cat);
                String traducao = traducaoService.buscarCategoriaTraduzida(categoria, linguaOriginal, "pt-BR");
                categoriasTraduzidas.add(traducao);
            }

            livro.setCategorias(categoriasTraduzidas);


            for (int i = 0; i < autores.size(); i++) {
                String autor = TextoUtils.normalizar(autores.get(i));
                autores.set(i, autor);
            }

            for (String nmAutor : livro.getAutores()) {
                autorRepository.findByNmAutor(nmAutor).orElseGet(() -> autorRepository.save(new Autor(nmAutor)));
            }

            for (String categoria : livro.getCategorias()) {
                System.out.println(categoria);
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
                    dto.setThumbnailUrl(TextoUtils.garantirHttps(v.getLinksImages().getThumbnail()));
                }
            }

            if (dto.getLingua() == null) {
                if (v.getLingua() != null) {
                    dto.setLingua(v.getLingua());
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
                    dto.getCategorias() != null && !dto.getCategorias().isEmpty() &&
                    dto.getLingua() != null)
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