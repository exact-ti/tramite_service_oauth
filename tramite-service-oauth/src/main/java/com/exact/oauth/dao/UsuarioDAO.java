package com.exact.oauth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exact.oauth.entity.Usuario;
import com.exact.oauth.repository.IUsuarioRepository;

@Component
public class UsuarioDAO {
	
	@Autowired
	IUsuarioRepository usuarioRepo;
	
	public Usuario getUsuarioByName(String nombre) {
		return usuarioRepo.findByNombre(nombre);
	}
}
