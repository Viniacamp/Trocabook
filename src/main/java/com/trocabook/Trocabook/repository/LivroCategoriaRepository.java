package com.trocabook.Trocabook.repository;

import com.trocabook.Trocabook.model.Categoria;
import com.trocabook.Trocabook.model.LivroCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroCategoriaRepository extends JpaRepository<LivroCategoria, Integer> {

    List<LivroCategoria> findByCategoria(Categoria categoria);
}
