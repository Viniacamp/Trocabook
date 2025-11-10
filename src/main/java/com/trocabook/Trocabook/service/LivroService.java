package com.trocabook.Trocabook.service;

import com.trocabook.Trocabook.model.*;
import com.trocabook.Trocabook.model.dto.GoogleBooksResponse;
import com.trocabook.Trocabook.model.dto.LivroDTO;
import com.trocabook.Trocabook.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importação adicionada
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LivroService {

    // --- Logger (Boa Prática) ---
    private static final Logger logger = LoggerFactory.getLogger(LivroService.class);

    // --- Constante (Boa Prática) ---
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    // --- Repositórios e Serviços ---
    private final LivroRepository livroRepository;
    private final UsuarioLivroRepository usuarioLivroRepository;
    private final FileStorageServiceUsuario fileStorageService;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LivroAutorRepository livroAutorRepository;
    private final LivroCategoriaRepository livroCategoriaRepository;
    private final RestTemplate restTemplate;

    // @Autowired é opcional em construtores no Spring moderno
    public LivroService(LivroRepository livroRepository, UsuarioLivroRepository usuarioLivroRepository,
                        FileStorageServiceUsuario fileStorageService,
                        RestTemplate restTemplate,
                        AutorRepository autorRepository, LivroCategoriaRepository livroCategoriaRepository,
                        CategoriaRepository categoriaRepository, LivroAutorRepository livroAutorRepository) {
        this.livroRepository = livroRepository;
        this.usuarioLivroRepository = usuarioLivroRepository;
        this.fileStorageService = fileStorageService;
        this.restTemplate = restTemplate;
        this.autorRepository = autorRepository;
        this.livroCategoriaRepository = livroCategoriaRepository;
        this.categoriaRepository = categoriaRepository;
        this.livroAutorRepository = livroAutorRepository;
    }


    @Transactional // Garante que ou tudo é salvo, ou nada é
    public Livro cadastrarNovoLivro(String nmLivro, Integer anoPublicacao, MultipartFile capaArquivo) throws IOException {
        String caminhoDaCapa = fileStorageService.armazenarArquivoLivro(capaArquivo);

        Livro novoLivro = new Livro();
        novoLivro.setNmLivro(nmLivro);
        novoLivro.setAnoPublicacao(anoPublicacao);
        novoLivro.setCapa(caminhoDaCapa);

        return livroRepository.save(novoLivro);
    }

    @Transactional // Garante que a criação do livro e da relação sejam atômicas
    public void anunciarNovoLivro(Livro livro, MultipartFile capaArquivo, Usuario usuario, String tipoNegociacao) throws IOException {
        // 1. Salva o arquivo da capa e obtém o caminho (String)
        String caminhoDaCapa = fileStorageService.armazenarArquivoLivro(capaArquivo);
        livro.setCapa(caminhoDaCapa);

        // 2. Salva a entidade Livro no banco de dados
        livroRepository.save(livro);

        // 3. Cria a relação entre o usuário e o livro
        UsuarioLivro usuarioLivro = new UsuarioLivro();
        // Usando cópias defensivas para segurança
        usuarioLivro.setUsuario(new Usuario(usuario));
        usuarioLivro.setLivro(new Livro(livro));
        usuarioLivro.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf(tipoNegociacao));

        // 4. Salva a relação no banco de dados
        usuarioLivroRepository.save(usuarioLivro);
    }

    @Transactional // Garante que o livro e todas as suas relações (autor, categoria) sejam salvas
    public Livro anunciarLivroApi(String titulo){
        GoogleBooksResponse respApi = this.buscarLivro(titulo);

        if (respApi == null || respApi.getItens() == null || respApi.getItens().isEmpty()) {
            logger.warn("Nenhum item encontrado na API do Google Books para o título: {}", titulo);
            return null;
        }
        logger.debug("Itens encontrados: {}", respApi.getItens().size());

        GoogleBooksResponse.Item primeiroItem = respApi.getItens().getFirst();
        GoogleBooksResponse.VolumeInfo info = primeiroItem.getVolumeInfo();

        // ❗ Procura se o livro já existe no banco
        Livro livroExistente = livroRepository.findByNmLivroIgnoreCase(info.getTitulo()).orElse(null);
        if (livroExistente != null) {
            logger.info("Livro '{}' já existe no banco de dados. Retornando existente.", info.getTitulo());
            return livroExistente;
        }

        // 📌 Primeiro tenta pegar a thumbnail do primeiro volume
        String capa = (info.getLinksImages() != null && info.getLinksImages().getThumbnail() != null)
                ? info.getLinksImages().getThumbnail()
                : null;

        List<String> categorias = info.getCategorias() != null ? new ArrayList<>(info.getCategorias()) : new ArrayList<>();
        List<String> autores = info.getAutores() != null ? new ArrayList<>(info.getAutores()) : new ArrayList<>();

        // 📌 Se o primeiro item não tiver capa, procura nos outros
        if (capa == null) {
            for (GoogleBooksResponse.Item item : respApi.getItens()) {
                GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
                if (v != null && v.getLinksImages() != null && v.getLinksImages().getThumbnail() != null) {
                    capa = v.getLinksImages().getThumbnail();
                    break;
                }
            }
        }

        // ... Lógica similar para categorias e autores ...
        if (categorias.isEmpty()) {
            for (GoogleBooksResponse.Item item : respApi.getItens()) {
                GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
                if (v != null && v.getCategorias() != null && !v.getCategorias().isEmpty()) {
                    categorias.addAll(v.getCategorias()); // Adiciona todas, não só substitui
                    break;
                }
            }
        }

        if (autores.isEmpty()) {
            for (GoogleBooksResponse.Item item : respApi.getItens()) {
                GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
                if (v != null && v.getAutores() != null && !v.getAutores().isEmpty()) {
                    autores.addAll(v.getAutores());
                    break;
                }
            }
        }

        logger.debug("Autores encontrados: {}", autores);
        logger.debug("Categorias encontradas: {}", categorias);

        // 📌 Caso nenhum item tenha capa, usa capa padrão do sistema
        if (capa == null) {
            capa = "/img/default-capa.png"; // Considere definir isso como uma constante também
        }

        // ✅ Cria novo livro
        Livro livro = new Livro();
        livro.setNmLivro(info.getTitulo());
        livro.setCapa(capa);
        livro.setAnoPublicacao(1980); // TODO: Tentar extrair o ano da data de publicação

        // ✅ Converte diferentes tipos de datas da API
        String data = info.getDataPublicacao();
        try {
            if (data == null || data.isEmpty()) {
                livro.setDataPublicacao(LocalDate.now()); // Fallback
            } else if (data.length() == 4) { // Apenas ano (YYYY)
                livro.setDataPublicacao(LocalDate.parse(data + "-01-01"));
                livro.setAnoPublicacao(Integer.parseInt(data)); // Atualiza o ano de publicação
            } else if (data.length() == 7) { // Ano e Mês (YYYY-MM)
                livro.setDataPublicacao(LocalDate.parse(data + "-01"));
                livro.setAnoPublicacao(Integer.parseInt(data.substring(0, 4)));
            } else { // Data completa (YYYY-MM-DD)
                livro.setDataPublicacao(LocalDate.parse(data));
                livro.setAnoPublicacao(Integer.parseInt(data.substring(0, 4)));
            }
        } catch (Exception e) {
            logger.warn("Erro ao parsear data de publicação '{}': {}", data, e.getMessage());
            livro.setDataPublicacao(LocalDate.now());
        }

        logger.info("Criando novo livro: {}", livro.getNmLivro());
        livroRepository.save(livro);

        // ✅ Autores (Find or Create)
        for (String nomeAutor : autores) {
            Autor autor = autorRepository.findByNmAutor(nomeAutor)
                    .orElseGet(() -> {
                        logger.info("Criando novo autor: {}", nomeAutor);
                        return autorRepository.save(new Autor(nomeAutor));
                    });

            LivroAutor rel = new LivroAutor();
            rel.setLivro(new Livro(livro)); // Cópia defensiva
            rel.setAutor(new Autor(autor)); // Cópia defensiva
            livroAutorRepository.save(rel);
        }

        // ✅ Categorias (Find or Create)
        for (String nmCategoria : categorias) {
            Categoria categoria = categoriaRepository.findByNmCategoria(nmCategoria)
                    .orElseGet(() -> {
                        logger.info("Criando nova categoria: {}", nmCategoria);
                        return categoriaRepository.save(new Categoria(nmCategoria));
                    });

            LivroCategoria rel = new LivroCategoria();
            rel.setLivro(new Livro(livro)); // Cópia defensiva
            rel.setCategoria(new Categoria(categoria)); // Cópia defensiva
            livroCategoriaRepository.save(rel);
        }

        return livro;
    }

    public List<LivroDTO> listarLivrosApi(String titulo) {
        GoogleBooksResponse respApi = this.buscarLivro(titulo);

        if (respApi == null || respApi.getItens() == null || respApi.getItens().isEmpty()) {
            return Collections.emptyList();
        }

        List<LivroDTO> livros = new ArrayList<>();
        for (GoogleBooksResponse.Item item : respApi.getItens()) {
            GoogleBooksResponse.VolumeInfo v = item.getVolumeInfo();
            if (v == null || v.getTitulo() == null){
                continue;
            }
            // Evita adicionar duplicatas exatas de título na mesma chamada
            if (livros.stream().anyMatch(lDto -> lDto.getTitulo().equalsIgnoreCase(v.getTitulo()))) {
                continue;
            }

            // Verifica se o livro já existe localmente
            Livro livroExistente = livroRepository.findByNmLivroIgnoreCase(v.getTitulo()).orElse(null);
            if (livroExistente != null) {
                LivroDTO livroDTO = new LivroDTO(livroExistente);
                livros.add(livroDTO);
                continue;
            }

            // Cria DTO a partir da API
            LivroDTO livro = new LivroDTO();
            livro.setTitulo(v.getTitulo());
            livro.setAutores((v.getAutores() == null || v.getAutores().isEmpty()) ? null : v.getAutores());
            livro.setCategorias((v.getCategorias() == null || v.getCategorias().isEmpty()) ? null : v.getCategorias());
            livro.setThumbnailUrl((v.getLinksImages() == null || v.getLinksImages().getThumbnail() == null)? null : v.getLinksImages().getThumbnail());

            String data = v.getDataPublicacao();
            try {
                if (data == null || data.isEmpty()) livro.setDataPublicacao(LocalDate.now());
                else if (data.length() == 4) livro.setDataPublicacao(LocalDate.parse(data + "-01-01"));
                else if (data.length() == 7) livro.setDataPublicacao(LocalDate.parse(data + "-01"));
                else livro.setDataPublicacao(LocalDate.parse(data));
            } catch (Exception e) {
                livro.setDataPublicacao(LocalDate.now());
            }

            // --- OTIMIZAÇÃO APLICADA ---
            // Tenta buscar dados faltantes usando a resposta que JÁ TEMOS, sem nova chamada de rede
            if (livro.getAutores() == null || livro.getCategorias() == null || livro.getThumbnailUrl() == null) {
                livro = buscarDadosFaltantes(livro, respApi);
            }

            livros.add(livro);
        }
        return livros;
    }

    // --- ASSINATURA ALTERADA (Otimização) ---
    private LivroDTO buscarDadosFaltantes(LivroDTO dto, GoogleBooksResponse resp) {
        // Não faz nova chamada de rede, apenas re-varre a resposta original
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

        // Se ainda assim faltar capa, define a padrão
        if (dto.getThumbnailUrl() == null) {
            dto.setThumbnailUrl("/img/default-capa.png");
        }

        return dto;
    }


    public GoogleBooksResponse buscarLivro(String titulo){
        // Usa a constante estática
        String urlCompleta = GOOGLE_BOOKS_API_URL + titulo;
        logger.info("Buscando livros na API: {}", urlCompleta);

        ResponseEntity<GoogleBooksResponse> resposta = restTemplate.exchange(
                urlCompleta,
                HttpMethod.GET,
                null,
                GoogleBooksResponse.class
        );

        if (resposta.getBody() != null && resposta.getBody().getItens() != null){
            logger.debug("Itens retornados da API: {}", resposta.getBody().getItens().size());
        } else {
            logger.warn("Chamada à API não retornou itens.");
        }

        return resposta.getBody();
    }
}