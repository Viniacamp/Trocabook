package com.trocabook.Trocabook.repository;

import com.trocabook.Trocabook.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByNmCategoriaOriginal(String nm);
    Optional<Categoria> findByNmCategoriaTraduzida(String nm);
}
