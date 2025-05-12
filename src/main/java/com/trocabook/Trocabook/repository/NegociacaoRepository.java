package com.trocabook.Trocabook.repository;

import com.trocabook.Trocabook.model.Negociacao;
import com.trocabook.Trocabook.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegociacaoRepository extends CrudRepository<Negociacao, Integer> {
    @Query("SELECT COUNT(n) FROM Negociacao n WHERE (n.usuarioAnunciante = :usuario OR n.usuarioInteressado = :usuario) AND n.tipo = :tipo")
    Long contarNegociacoesPorUsuarioETipo(@Param("usuario") Usuario usuario, @Param("tipo") Negociacao.Tipo tipo);
    List<Negociacao> findByUsuarioAnunciante(Usuario usuario);
    List<Negociacao> findByUsuarioAnuncianteAndTipo(Usuario usuario, Negociacao.Tipo tipo);
}
