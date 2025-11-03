package com.trocabook.Trocabook.repository;

import com.trocabook.Trocabook.model.Autor;
import com.trocabook.Trocabook.model.LivroAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroAutorRepository extends JpaRepository<LivroAutor, Integer> {
    List<LivroAutor> findByAutor(Autor autor);
}
