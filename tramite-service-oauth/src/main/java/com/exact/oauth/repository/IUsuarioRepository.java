package com.exact.oauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.oauth.entity.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {

	Usuario findByNombre(String nombre);

}
