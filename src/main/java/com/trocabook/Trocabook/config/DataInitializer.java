package com.trocabook.Trocabook.config;

import com.trocabook.Trocabook.model.Livro;
import com.trocabook.Trocabook.model.Negociacao;
import com.trocabook.Trocabook.model.Usuario;
import com.trocabook.Trocabook.model.UsuarioLivro;
import com.trocabook.Trocabook.repository.LivroRepository;
import com.trocabook.Trocabook.repository.NegociacaoRepository;
import com.trocabook.Trocabook.repository.UsuarioLivroRepository;
import com.trocabook.Trocabook.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioLivroRepository usuarioLivroRepository;

    @Autowired
    private NegociacaoRepository negociacaoRepository;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0 || livroRepository.count() > 0) {
            return;
        }

        // --- USUÁRIOS COM DADOS CORRIGIDOS ---
        Usuario u1 = new Usuario();
        u1.setNmUsuario("squirtle");
        u1.setEmail("squirtle@gmail.com");
        u1.setSenha("Squirtle@123");
        u1.setCPF("087.382.730-98");     // <-- CPF COM MÁSCARA
        u1.setFoto("/img/vendedor1.svg");
        u1.setAvaliacao(4.8);
        u1.setStatus('A');

        Usuario u2 = new Usuario();
        u2.setNmUsuario("PedroLucas");
        u2.setEmail("pedrol@gmail.com");
        u2.setSenha("Pedro@456");
        u2.setCPF("796.895.940-36");    // <-- CPF COM MÁSCARA
        u2.setFoto("/img/vendedor2.svg");
        u2.setAvaliacao(4.7);
        u2.setStatus('A');

        Usuario u3 = new Usuario();
        u3.setNmUsuario("Rafaela");
        u3.setEmail("rafa@gmail.com");
        u3.setSenha("Rafaela@789");
        u3.setCPF("336.443.280-56");   // <-- CPF COM MÁSCARA
        u3.setFoto("/img/vendedor3.svg");
        u3.setAvaliacao(4.9);
        u3.setStatus('A');

        Usuario u4 = new Usuario();
        u4.setNmUsuario("Vinicius");
        u4.setEmail("vini@gmail.com");
        u4.setSenha("Vini@101");
        u4.setCPF("189.621.590-40");   // <-- CPF COM MÁSCARA
        u4.setFoto("/img/vendedor4.svg");
        u4.setAvaliacao(4.84);
        u4.setStatus('A');

        Usuario u5 = new Usuario();
        u5.setNmUsuario("Welligton");
        u5.setEmail("well@gmail.com");
        u5.setSenha("Well@202");
        u5.setCPF("801.049.840-82");   // <-- CPF COM MÁSCARA
        u5.setFoto("/img/vendedor5.svg");
        u5.setAvaliacao(4.7);
        u5.setStatus('A');

        Usuario u6 = new Usuario();
        u6.setNmUsuario("Gpt");
        u6.setEmail("gpt@gmail.com");
        u6.setSenha("GptBot@2025!");
        u6.setCPF("598.833.780-50");     // <-- CPF COM MÁSCARA
        u6.setFoto("/img/vendedor6.svg");
        u6.setAvaliacao(0);
        u6.setStatus('A');
        // --- O RESTANTE DO CÓDIGO PERMANECE O MESMO ---

        Livro l1 = new Livro();
        l1.setNmLivro("A Era da IA: e nosso futuro como humanos");
        l1.setAnoPublicacao(1980);
        l1.setCapa("/img/IALIVRO.png");

        Livro l2 = new Livro();
        l2.setNmLivro("A Floresta Sombria");
        l2.setAnoPublicacao(1980);
        l2.setCapa("/img/livro2.png");

        Livro l3 = new Livro();
        l3.setNmLivro("A Culpa é das Estrelas");
        l3.setAnoPublicacao(1980);
        l3.setCapa("/img/culpa.jpg");

        Livro l4 = new Livro();
        l4.setNmLivro("O Pequeno Príncipe");
        l4.setAnoPublicacao(1980);
        l4.setCapa("/img/livro4.png");

        Livro l5 = new Livro();
        l5.setNmLivro("365 Reflexões Estóica");
        l5.setAnoPublicacao(1980);
        l5.setCapa("/img/livro6.png");

        Livro l6 = new Livro();
        l6.setNmLivro("Dom Casmurro");
        l6.setAnoPublicacao(1980);
        l6.setCapa("/img/dom casmurro.jpg");

        Livro l7 = new Livro();
        l7.setNmLivro("Coletânea Harry Potter");
        l7.setAnoPublicacao(1980);
        l7.setCapa("/img/harry.jpg");

        Livro l8 = new Livro();
        l8.setNmLivro("Fundamentos de html5 e css3");
        l8.setAnoPublicacao(1980);
        l8.setCapa("/img/capa-ampliada-9788575224380.jpg");

        Livro l9 = new Livro();
        l9.setNmLivro("Sommerville - Engenharia de Software");
        l9.setAnoPublicacao(1980);
        l9.setCapa("/img/engenharia de software.jpg");

        Livro l10 = new Livro();
        l10.setNmLivro("Livro Senhor dos Aneis A Sociedade do Anel");
        l10.setAnoPublicacao(1980);
        l10.setCapa("/img/61460909.jpg");

        LinkedList<Livro> livros = new LinkedList<>();
        LinkedList<Usuario> usuarios = new LinkedList<>();

        livros.addAll(Arrays.asList(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10));
        usuarios.addAll(Arrays.asList(u1, u2, u3, u4, u5, u6));

        usuarioRepository.saveAll(usuarios);
        livroRepository.saveAll(livros);

        UsuarioLivro ul1 = new UsuarioLivro();
        ul1.setUsuario(u1);
        ul1.setLivro(l1);
        ul1.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("TROCA"));
        usuarioLivroRepository.save(ul1);

        UsuarioLivro ul2 = new UsuarioLivro();
        ul2.setUsuario(u1);
        ul2.setLivro(l2);
        ul2.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("VENDA"));
        usuarioLivroRepository.save(ul2);

        UsuarioLivro ul3 = new UsuarioLivro();
        ul3.setUsuario(u2);
        ul3.setLivro(l3);
        ul3.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("TROCA"));
        usuarioLivroRepository.save(ul3);

        UsuarioLivro ul4 = new UsuarioLivro();
        ul4.setUsuario(u2);
        ul4.setLivro(l4);
        ul4.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("TROCA"));
        usuarioLivroRepository.save(ul4);

        UsuarioLivro ul5 = new UsuarioLivro();
        ul5.setUsuario(u3);
        ul5.setLivro(l5);
        ul5.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("AMBOS"));
        usuarioLivroRepository.save(ul5);

        UsuarioLivro ul6 = new UsuarioLivro();
        ul6.setUsuario(u3);
        ul6.setLivro(l6);
        ul6.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("TROCA"));
        usuarioLivroRepository.save(ul6);

        UsuarioLivro ul7 = new UsuarioLivro();
        ul7.setUsuario(u4);
        ul7.setLivro(l7);
        ul7.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("VENDA"));
        usuarioLivroRepository.save(ul7);

        UsuarioLivro ul8 = new UsuarioLivro();
        ul8.setUsuario(u4);
        ul8.setLivro(l8);
        ul8.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("AMBOS"));
        usuarioLivroRepository.save(ul8);

        UsuarioLivro ul9 = new UsuarioLivro();
        ul9.setUsuario(u5);
        ul9.setLivro(l9);
        ul9.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("TROCA"));
        usuarioLivroRepository.save(ul9);

        UsuarioLivro ul10 = new UsuarioLivro();
        ul10.setUsuario(u5);
        ul10.setLivro(l10);
        ul10.setTipoNegociacao(UsuarioLivro.TipoNegociacao.valueOf("VENDA"));
        usuarioLivroRepository.save(ul10);

        Livro l11 = new Livro();
        l11.setCapa("/img/livro5.png");
        l11.setAnoPublicacao(1980);
        l11.setNmLivro("Dracula");
        livroRepository.save(l11);

        Negociacao n1 = new Negociacao();
        n1.setUsuarioAnunciante(u1);
        n1.setUsuarioInteressado(u2);
        n1.setLivro(l11);
        n1.setTipo(Negociacao.Tipo.valueOf("TROCA"));

        negociacaoRepository.save(n1);

        Livro l12 = new Livro();
        l12.setCapa("/img/livro3.png");
        l12.setAnoPublicacao(1980);
        l12.setNmLivro("Harry Potter e a Pedra Filososfal");
        livroRepository.save(l12);

        Negociacao n2 = new Negociacao();
        n2.setUsuarioAnunciante(u3);
        n2.setUsuarioInteressado(u4);
        n2.setLivro(l12);
        n2.setTipo(Negociacao.Tipo.valueOf("VENDA"));

        negociacaoRepository.save(n2);

        Livro l13 = new Livro();
        l13.setCapa("/img/image-15@2x.png");
        l13.setAnoPublicacao(1980);
        l13.setNmLivro("A Garota do Lago");
        livroRepository.save(l13);

        Negociacao n3 = new Negociacao();
        n3.setUsuarioAnunciante(u3);
        n3.setUsuarioInteressado(u5);
        n3.setLivro(l13);
        n3.setTipo(Negociacao.Tipo.valueOf("TROCA"));

        negociacaoRepository.save(n3);

    }
}
