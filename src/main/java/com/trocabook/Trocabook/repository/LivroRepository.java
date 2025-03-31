package com.trocabook.Trocabook.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trocabook.Trocabook.model.Livro;

@Repository
public interface LivroRepository extends CrudRepository<Livro, Integer> {

}
