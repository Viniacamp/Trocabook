package com.trocabook.Trocabook.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trocabook.Trocabook.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	Usuario findByEmailAndSenha(String email, String senha);
}
