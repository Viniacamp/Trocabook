package com.trocabook.Trocabook.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trocabook.Trocabook.model.Livro;

import java.util.List;

@Repository
public interface LivroRepository extends CrudRepository<Livro, Integer> {
     List<Livro> findByNmLivroContainingIgnoreCase(String nm_livro);
}
